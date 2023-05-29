package com.siasg.comprasnet.ui.fragment.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.siasg.comprasnet.R
import com.siasg.comprasnet.databinding.FragmentDetailsBinding
import com.siasg.comprasnet.di.ComprasApiService
import com.siasg.comprasnet.domain.Contrato
import com.siasg.comprasnet.domain.PostgrestMessageApi
import com.siasg.comprasnet.domain.PostgrestMessageApiImpl
import com.siasg.comprasnet.repository.FavoriteContratosRepository
import com.siasg.comprasnet.ui.adapter.DetailsAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.reflect.full.memberProperties

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    val args: DetailsFragmentArgs by navArgs()
    private lateinit var progressBar: ProgressBar
    private var isFavorite = false
    private lateinit var recyclerView: RecyclerView
    private lateinit var id: String
    private lateinit var button: MaterialButton

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
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding.fragment = this
        binding.lifecycleOwner = viewLifecycleOwner

        messageApi = PostgrestMessageApiImpl()

        recyclerView = binding.rvDetails
        recyclerView.layoutManager = LinearLayoutManager(context)

        id = args.idArgs.toString()
        button = binding.toggleButtonDetails

        progressBar = binding.pbLogoDetails
        progressBar.visibility = View.VISIBLE

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch(IO) {
            try {
                val response = comprasApiService.getSingleContratoID(id).execute()
                val contrato = response.body()

                withContext(Main) {
                    if (contrato != null) {
                        favoriteContratosRepository.isFavoriteContrato(contrato).let {
                            isFavorite = it
                            if (it) {
                                button.text = getString(R.string.button_state_yes)
                                button.isChecked = true
                            } else {
                                button.text = getString(R.string.button_state_no)
                                button.isChecked = false
                            }

                            button.setOnClickListener {
                                button.isEnabled = false

                                lifecycleScope.launch(IO) {
                                    val currentUser = client.gotrue.currentSessionOrNull()
                                    if (currentUser == null) {
                                        withContext(Main) {
                                            Toast.makeText(
                                                requireContext(),
                                                "Você precisa estar logado para favoritar",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    } else {
                                        if (isFavorite) {
                                            favoriteContratosRepository.removeFavoriteContrato(
                                                contrato
                                            )
                                        } else {
                                            favoriteContratosRepository.addFavoriteContrato(contrato)
                                        }

                                        messageApi.updateFavorites(
                                            client.gotrue.retrieveUserForCurrentSession().id,
                                            favoriteContratosRepository.getAllFavoriteContrato(),
                                            client.postgrest["userData"]
                                        )

                                        withContext(Main) {
                                            button.isChecked = !isFavorite
                                            isFavorite = !isFavorite
                                            if (isFavorite)
                                                button.text = getString(R.string.button_state_yes)
                                            else
                                                button.text = getString(R.string.button_state_no)
                                        }
                                    }
                                }
                                button.isEnabled = true
                            }
                        }

                        val detailsList = mutableListOf<Pair<String, String>>()

                        val labelMap = mapOf(
                            "id" to "ID:",
                            "categoria" to "Categoria: ",
                            "codigo_contrato" to "Código de Contrato: ",
                            "unidade_compra" to "Unidade Compra:",
                            "orgao_codigo" to "Código do Órgão:",
                            "orgao_nome" to "Órgão:",
                            "unidade_codigo" to "Código da Unidade:",
                            "unidade_nome" to "Nome da Unidade:",
                            "unidade_nome_resumido" to "Sigla Unidade:",
                            "fornecedor_tipo" to "Tipo do Fornecedor:",
                            "fornecedor_cnpj_cpf_idgener" to "CNPJ/CPF do Fornecedor:",
                            "fornecedor_nome" to "Nome do Fornecedor:",
                            "modalidade" to "Modalidade:",
                            "licitacao_numero" to "Número da Licitação:",
                            "tipo" to "Tipo:",
                            "receita_despesa" to "Receita ou Despesa?:",
                            "data_assinatura" to "Data da Assinatura:",
                            "data_publicacao" to "Data da Publicação:",
                            "vigencia_inicio" to "Início da Vigência:",
                            "vigencia_fim" to "Fim da Vigência:",
                            "codigo_tipo" to "Código do Tipo:",
                            "modalidade_codigo" to "Código de Modalidade:",
                            "numero" to "Número:",
                            "processo" to "Processo:",
                            "unidade_origem_codigo" to "Código da Unidade Origem:",
                            "unidade_origem_nome" to "Nome da Unidade Origem:",
                            "valor_acumulado" to "Valor Acumulado:",
                            "valor_global" to "Valor Global:",
                            "valor_inicial" to "Valor Inicial:",
                            "valor_parcela" to "Valor da Parcela:",
                            "num_parcelas" to "Número de Parcelas:",
                            "codigo_compra" to "Código de Compra: ",
                            "fundamento_legal" to "Fundamento Legal:",
                            "informacao_complementar" to "Informação Complementar:",
                            "objeto" to "Objeto/Descrição:"
                        )

                        for (prop in Contrato::class.memberProperties) {
                            val value = prop.get(contrato)
                            val label = labelMap[prop.name] ?: prop.name
                            detailsList.add(label to value.toString())
                        }

                        withContext(Main) {
                            binding.adapter = DetailsAdapter(detailsList)
                            binding.rvDetails.layoutManager = LinearLayoutManager(context)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e("Fragmento_Details", "erro api detalhe contratos", e)
                withContext(Main) {
                    Toast.makeText(
                        requireContext(),
                        "Erro ao obter detalhes do contrato",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } finally {
                withContext(Main) {
                    progressBar.visibility = View.GONE
                }
            }
        }

    }

    fun share (v: View){
        val textToShare = "Olá, estou compartilhando o contrato ${args.idArgs} com você!\nhttps://compras.dados.gov.br/comprasContratos/doc/contrato/${args.idArgs}"

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, textToShare)

        startActivity(Intent.createChooser(shareIntent, "Compartilhar contrato via"))

    }

    fun back(v: View) {
        findNavController().navigateUp()
    }

}
