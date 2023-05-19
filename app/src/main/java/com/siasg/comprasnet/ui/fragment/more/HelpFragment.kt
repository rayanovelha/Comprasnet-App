package com.siasg.comprasnet.ui.fragment.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.siasg.comprasnet.databinding.FragmentHelpBinding

class HelpFragment : Fragment() {

    private lateinit var binding: FragmentHelpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHelpBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.fragment = this

        val text = "<br>" +
                "<big><i><b><font color='black'>Qual é o objetivo desse aplicativo?</font></b></i></big><br>" +
                "<font color='#646464';align='justify'>&nbsp; O objetivo do aplicativo móvel desenvolvido é " +
                "permitir que funcionários públicos e cidadãos comuns fiscalizem e consultem os contratos públicos realizados pelo governo federal. Ele fornece acesso " +
                "simplificado e intuitivo a informações sobre valores, gestores, vencimentos e categorização dos contratos.</font><br><br>" +
                "<big><i><b><font color='black'>Quais dados são disponibilizados pelo Sistema Integrado de Administração e Serviços Gerais (SIASG)?</font></b></i></big><br>" +
                "<font color='#646464';align='justify'>&nbsp; O SIASG disponibiliza diversos dados relacionados às compras governamentais. Através da API de Compras Governamentais, " +
                "é possível acessar informações sobre fornecedores, materiais, serviços, licitações, pregões, contratos, compras sem licitação e o plano anual de contratações.</font><br><br>" +
                "<big><i><b><font color='black'>Como é a experiência do usuário no aplicativo?</font></b></i></big><br>" +
                "<font color='#646464';align='justify'>&nbsp; O aplicativo foi projetado para fornecer uma experiência de usuário amigável. Possui uma interface intuitiva e fluida, utilizando o " +
                "componente de navegação da biblioteca Android Jetpack para facilitar o acesso às funcionalidades. O aplicativo é dividido em seções, como Início, Minha Conta, Favoritos e Mais, e " +
                "apresenta os resultados dos contratos de forma clara e sintetizada através de cartões informativos.</font><br><br>"

        binding.tvHelpData.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)

        return binding.root
    }

}