package com.siasg.comprasnet.ui.fragment.more

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.siasg.comprasnet.databinding.FragmentFavoritesBinding
import com.siasg.comprasnet.di.ComprasApiService
import com.siasg.comprasnet.domain.Contrato
import com.siasg.comprasnet.domain.PostgrestMessageApi
import com.siasg.comprasnet.domain.PostgrestMessageApiImpl
import com.siasg.comprasnet.repository.FavoriteContratosRepository
import com.siasg.comprasnet.ui.adapter.ContratosListAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var buttonError: Button
    private lateinit var cardError: CardView
    private lateinit var buttonEmpty: Button
    private lateinit var cardEmpty: CardView

    @Inject
    lateinit var comprasApiService: ComprasApiService

    @Inject
    lateinit var favoriteContratosRepository: FavoriteContratosRepository

    @Inject
    lateinit var client: SupabaseClient

    private lateinit var messageApi: PostgrestMessageApi

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        binding.fragment = this
        binding.lifecycleOwner = viewLifecycleOwner

        buttonError = binding.btErrorFavorites

        cardError = binding.cardErrorFavorites
        cardError.visibility = View.GONE
        cardError.isEnabled = false

        buttonEmpty = binding.btEmptyFavorites

        cardEmpty = binding.cardEmptyFavorites
        cardEmpty.visibility = View.GONE
        cardEmpty.isEnabled = false

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        if (client.gotrue.currentSessionOrNull() == null) {

            showCardError()

        } else {

            recyclerView = binding.rvFavorites
            recyclerView.layoutManager = LinearLayoutManager(context)

            messageApi = PostgrestMessageApiImpl()

            loadFavoritesContratos()

        }
    }

    private fun loadFavoritesContratos() {

        progressBar = binding.pbLogoFavs
        progressBar.visibility = View.VISIBLE

        lifecycleScope.launch(IO) {

            try {

                val responseDAO = favoriteContratosRepository.getAllFavoriteContrato()
                val listaContratos = mutableListOf<Contrato>()
                val responsePostgrest = messageApi.retrieve(
                    client.gotrue.retrieveUserForCurrentSession().id,
                    client.postgrest["userData"]
                )

                /** se o banco de dados local está vazio */
                if (responseDAO.isEmpty()) {

                    /** se a nuvem estiver vazia */
                    if (responsePostgrest.favorites.isEmpty())
                        withContext(Main) {
                            showCardEmpty()
                        }
                    else
                    /** se a nuvem não estiver vazia */
                    {
                        responsePostgrest.favorites.split(",").forEach {
                                val response = comprasApiService.getSingleContratoID(it).execute()
                                val contrato = response.body()!!
                                listaContratos.add(contrato)
                                favoriteContratosRepository.addFavoriteContrato(contrato)
                        }
                        showFavoritesContratos(listaContratos)
                    }

                } else
                /** se o banco de dados local não está vazio */
                {

                    val favoritesCloud = responsePostgrest.favorites
                    val favoritesLocal = responseDAO.joinToString(separator = ",") { it.id }
                    //Toast.makeText(context, "Carregando contratos favoritados", Toast.LENGTH_SHORT).show()

                    /** se o banco de dados local está igual ao da nuvem */
                    if (favoritesLocal == favoritesCloud) {
                        responsePostgrest.favorites.split(",").forEach {
                            val response = comprasApiService.getSingleContratoID(it).execute()
                            val contrato = response.body()!!
                            listaContratos.add(contrato)
                        }
                        showFavoritesContratos(listaContratos)

                    } else {
                        /** se o banco de dados local estiver diferente da nuvem */
                        favoriteContratosRepository.cleanFavoriteContrato()
                        favoritesCloud.split(",").forEach {
                            val response = comprasApiService.getSingleContratoID(it).execute()
                            val contrato = response.body()!!
                            listaContratos.add(contrato)
                            favoriteContratosRepository.addFavoriteContrato(contrato)
                        }
                        showFavoritesContratos(listaContratos)
                    }
                }
            } catch (e: IOException) {
                Log.e("Fragmento_Favoritos", "Erro de I/O", e)
                withContext(Main) {
                    Toast.makeText(context, "Erro de I/O", Toast.LENGTH_SHORT).show()
                }
            } catch (e: HttpException) {
                Log.e("Fragmento_Favoritos", "Erro na API do governo", e)
                withContext(Main) {
                    Toast.makeText(context, "Erro na API do governo", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("Fragmento_Favoritos", "Erro. Tente novamente", e)
                withContext(Main) {
                    Toast.makeText(context, "Erro. Tente novamente", Toast.LENGTH_SHORT).show()
                }
            } finally {
                withContext(Main) {
                    progressBar.visibility = View.GONE
                }
            }
        }
    }

    private suspend fun showFavoritesContratos(listaContratos: MutableList<Contrato>) {
        withContext(Main) {
            recyclerView.adapter = ContratosListAdapter(
                listaContratos,
                this@FavoritesFragment,
                null
            )
        }
    }

    fun onContratoClick(contrato: Contrato) {
        val action = FavoritesFragmentDirections.actionFavoritesFragmentToDetailsFragment(contrato.id)
        findNavController().navigate(action)
    }

    fun irParaLogin(v: View) {
        val action = FavoritesFragmentDirections.actionFavoritesFragmentToLoginFragment()
        findNavController().navigate(action)
    }

    fun irParaHome(v: View){
        val action = FavoritesFragmentDirections.actionFavoritesFragmentToHomeFragment()
        findNavController().navigate(action)
    }

    private fun showCardError(){
        cardError.visibility = View.VISIBLE
        cardError.isEnabled = true
        buttonError.visibility = View.VISIBLE
        buttonError.isEnabled = true
    }

    private fun showCardEmpty(){
        cardEmpty.visibility = View.VISIBLE
        cardEmpty.isEnabled = true
        buttonEmpty.visibility = View.VISIBLE
        buttonEmpty.isEnabled = true
    }

}