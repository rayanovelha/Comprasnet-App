package com.siasg.comprasnet.ui.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.siasg.comprasnet.R
import com.siasg.comprasnet.databinding.FragmentForgotPasswordBinding
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.gotrue
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {

    private lateinit var binding: FragmentForgotPasswordBinding
    private var TAG: String = "ForgotPassword"

    @Inject
    lateinit var client: SupabaseClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.fragment = this
        return binding.root
    }

    private suspend fun passwordReset(email: String): Boolean {
        return kotlin.runCatching {
            client.gotrue.sendRecoveryEmail(email)
        }.onSuccess {
            withContext(Main) {
                Toast.makeText(
                    context,
                    "Um email de mudança de senha foi enviado para sua caixa de entrada",
                    Toast.LENGTH_LONG
                ).show()
            }
        }.onFailure {
            withContext(Main) {
                Toast.makeText(
                    context,
                    "Não foi possível enviar o email para mudança de senha",
                    Toast.LENGTH_LONG
                ).show()
            }
        }.isSuccess
    }

    @SuppressWarnings
    fun irParaLogin(v: View){
        val email = binding.TextEditEmailAddress.text.toString().trim()
        if (email.isEmpty())
            Toast.makeText(context, "Um dos campos se encontra vazio", Toast.LENGTH_SHORT).show()
        else {
            lifecycleScope.launch (IO) {
                if (passwordReset(email)){
                    withContext(Main){
                        findNavController().navigate(R.id.action_forgotPasswordFragment_to_loginFragment)
                    }
                }
            }
        }
    }
}