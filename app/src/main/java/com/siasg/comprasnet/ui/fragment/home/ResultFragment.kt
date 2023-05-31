package com.siasg.comprasnet.ui.fragment.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.siasg.comprasnet.R
import com.siasg.comprasnet.databinding.FragmentResultBinding
import com.siasg.comprasnet.domain.ApplicationDate
import com.siasg.comprasnet.domain.Contrato
import com.siasg.comprasnet.domain.ContratosResponse
import com.siasg.comprasnet.repository.Resource
import com.siasg.comprasnet.ui.adapter.ContratosListAdapter
import com.siasg.comprasnet.ui.adapter.OnContratoClickListener
import com.siasg.comprasnet.viewmodel.ComprasApiViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

@AndroidEntryPoint
class ResultFragment : Fragment(), OnContratoClickListener {

    private lateinit var binding: FragmentResultBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var applicationDate: ApplicationDate

    private val viewmodel: ComprasApiViewModel by viewModels()
    val args: ResultFragmentArgs by navArgs()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(inflater, container, false)
        binding.fragment = this
        binding.lifecycleOwner = viewLifecycleOwner

        recyclerView = binding.rvResultados
        recyclerView.layoutManager = LinearLayoutManager(context)

        progressBar = binding.pbLogo
        progressBar.visibility = View.GONE

        return binding.root

    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()

        val filter = args.filterArg
        val search = args.searchArg

        applicationDate = ApplicationDate()
        progressBar.visibility = View.VISIBLE

        lifecycleScope.launch(IO) {
            try {
                withTimeout(5000) {
                    when {
                        filter == 0 -> {
                            val resource = viewmodel.fetchTodosContratos()
                            showContratos(resource)
                        }
                        filter == 10 -> {
                            val resource = viewmodel.fetchContratosByObjeto(search)
                            withContext(Main) {
                                binding.tvResultados.text = getString(R.string.title_search) + search
                            }
                            showContratos(resource)
                        }
                        filter == 30 -> {
                            val resource = viewmodel.fetchContratosByDateMinMax(
                                applicationDate.getDate_Hoje(),
                                applicationDate.getVencimento_30()
                            )
                            showContratos(resource)
                        }
                        filter == 60 -> {
                            val resource = viewmodel.fetchContratosByDateMinMax(
                                applicationDate.getVencimento_30(),
                                applicationDate.getVencimento_60()
                            )
                            showContratos(resource)
                        }
                        filter == 90 -> {
                            val resource = viewmodel.fetchContratosByDateMinMax(
                                applicationDate.getVencimento_60(),
                                applicationDate.getVencimento_90()
                            )
                            showContratos(resource)
                        }
                        filter == 180 -> {
                            val resource = viewmodel.fetchContratosByDateMinMax(
                                applicationDate.getVencimento_90(),
                                applicationDate.getVencimento_180()
                            )
                            showContratos(resource)
                        }
                        else -> {
                            val resource = viewmodel.fetchContratosByDateMin(
                                applicationDate.getVencimento_180()
                            )
                            showContratos(resource)
                        }
                    }
                }

            } catch (e: TimeoutCancellationException) {
                withContext(Main) {
                    Toast.makeText(
                        context,
                        "API do governo demorando muito para responder. Volte e tente novamente.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                withContext(Main) {
                    Toast.makeText(
                        context,
                        "Ocorreu um ERRO ao requisitar os contratos. Tente novamente.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } finally {
                withContext(Main) {
                    progressBar.visibility = View.GONE
                }
            }
        }

    }

    override fun onContratoClick(contrato: Contrato) {
        val action = ResultFragmentDirections.actionResultFragmentToDetailsFragment(contrato.id)
        findNavController().navigate(action)
    }

    private fun showContratos(resource: LiveData<Resource<ContratosResponse?>>) {
        viewLifecycleOwner.lifecycleScope.launch(Main) {
            resource.observe(viewLifecycleOwner) { resource ->
                resource.dado?.let {
                    recyclerView.adapter = ContratosListAdapter(
                        it._embedded.contratos,
                        null,
                        this@ResultFragment
                    )
                }
                resource.erro?.let {
                    Toast.makeText(context, "API caiu", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}