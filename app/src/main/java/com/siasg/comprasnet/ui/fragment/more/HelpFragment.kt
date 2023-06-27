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
                "<big><i><b><font color='black'>1. O que é o aplicativo de consultas públicas de compras governamentais?</font></b></i></big><br>" +
                "<font color='#646464';align='justify'>&nbsp; O aplicativo é uma plataforma desenvolvida em Kotlin, de código aberto, que permite aos usuários acessar informações sobre contratos públicos do governo federal. Ele disponibiliza dados como quantidade de contratos, vencimentos, valores, categorização e fornecedores.</font><br><br>" +

                "<big><i><b><font color='black'>2. Quem pode utilizar o aplicativo?</font></b></i></big><br>" +
                "<font color='#646464';align='justify'>&nbsp; O aplicativo pode ser utilizado por funcionários públicos e cidadãos comuns que desejam fiscalizar e consultar os contratos públicos realizados pelo governo federal.</font><br><br>" +

                "<big><i><b><font color='black'>3. Qual é o objetivo do aplicativo?</font></b></i></big><br>" +
                "<font color='#646464';align='justify'>&nbsp; O objetivo principal do aplicativo é aumentar a transparência dos órgãos e entidades públicas federais, fornecendo acesso simplificado e intuitivo às informações sobre os contratos públicos. Ele visa contribuir para o aumento do nível de transparência pública.</font><br><br>" +

                "<big><i><b><font color='black'>4. Quais são os dados disponibilizados pelo aplicativo?</font></b></i></big><br>" +
                "<font color='#646464';align='justify'>&nbsp; O aplicativo disponibiliza informações como valores dos contratos, gestores responsáveis, datas de vencimento, categorias dos contratos e detalhes dos fornecedores envolvidos.</font><br><br>" +

                "<big><i><b><font color='black'>5. O aplicativo é gratuito?</font></b></i></big><br>" +
                "<font color='#646464';align='justify'>&nbsp; Sim, o aplicativo é gratuito e de código aberto. Isso significa que qualquer pessoa pode acessar, utilizar e contribuir para o desenvolvimento do aplicativo.</font><br><br>" +

                "<big><i><b><font color='black'>6. Quais tecnologias foram utilizadas no desenvolvimento do aplicativo?</font></b></i></big><br>" +
                "<font color='#646464';align='justify'>&nbsp; O aplicativo foi desenvolvido em Kotlin, uma linguagem de programação orientada a objetos, gratuita e de código aberto. Ele é executado na Java Virtual Machine, permitindo compatibilidade com códigos em Java e execução nativa no sistema operacional Android.</font><br><br>" +

                "<big><i><b><font color='black'>7. O aplicativo está disponível para qual sistema operacional?</font></b></i></big><br>" +
                "<font color='#646464';align='justify'>&nbsp; O aplicativo está disponível para smartphones Android.</font><br><br>" +

                "<big><i><b><font color='black'>8. Existe algum requisito específico para utilizar o aplicativo?</font></b></i></big><br>" +
                "<font color='#646464';align='justify'>&nbsp; Para utilizar o aplicativo, é necessário possuir um smartphone com sistema operacional Android e acesso à internet.</font><br><br>" +

                "<big><i><b><font color='black'>9. O aplicativo permite fazer pesquisas específicas?</font></b></i></big><br>" +
                "<font color='#646464';align='justify'>&nbsp; Sim, o aplicativo possui um recurso de busca que permite aos usuários pesquisarem contratos públicos específicos com base em palavras-chave.</font><br><br>" +

                "<big><i><b><font color='black'>10. Como posso contribuir para o desenvolvimento do aplicativo?</font></b></i></big><br>" +
                "<font color='#646464';align='justify'>&nbsp; Para contribuir com o desenvolvimento do aplicativo, você pode acessar o repositório oficial no GitHub, onde é possível obter o código-fonte, reportar problemas, sugerir melhorias e enviar solicitações de alterações.</font><br><br>" +

                "<big><i><b><font color='black'>11. O aplicativo requer autenticação para acessar os dados?</font></b></i></big><br>" +
                "<font color='#646464';align='justify'>  Não é necessário autenticação para navegação nos contatos. No entanto, o recurso de favoritar contratos exige autenticação.</font><br><br>" +

                "<big><i><b><font color='black'>12. Como posso marcar contratos como favoritos no aplicativo?</font></b></i></big><br>" +
                "<font color='#646464';align='justify'>  O aplicativo permite que o usuário marque contratos como favoritos para acessá-los rapidamente. Isso pode ser feito na tela de detalhes do contrato, onde é possível encontrar um botão para marcar ou desmarcar o contrato como favorito.</font><br><br>" +

                "<big><i><b><font color='black'>13. O aplicativo está vinculado a alguma plataforma governamental existente?</font></b></i></big><br>" +
                "<font color='#646464';align='justify'>  O aplicativo mencionado no artigo é desenvolvido de forma independente, não sendo diretamente vinculado a nenhuma plataforma governamental existente. No entanto, ele utiliza dados fornecidos pelo Sistema Integrado de Administração e Serviços Gerais (SIASG) por meio de sua API pública.</font><br><br>"


        binding.tvHelpData.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)

        return binding.root
    }

}