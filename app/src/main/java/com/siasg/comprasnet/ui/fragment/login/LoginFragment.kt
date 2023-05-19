package com.siasg.comprasnet.ui.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.siasg.comprasnet.R
import com.siasg.comprasnet.databinding.FragmentLoginBinding
import com.siasg.comprasnet.viewmodel.SupabaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private var TAG: String = "Login"
    private val viewModel: SupabaseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.fragment = this

        lifecycleScope.launch(Main){
            if (viewModel.isLogged()) {
                irParaConta()
            }
        }

        return binding.root
    }

    fun verificaCampos(): Boolean {
        val email: String = binding.TextEditInputEmailLogin.text.toString().trim()
        val password: String = binding.TextEditInputPasswordLogin.text.toString().trim()

        return if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "Login falhou, campos vazios", Toast.LENGTH_SHORT).show()
            false
        } else
            true
    }

    fun login(v: View){
        if (verificaCampos()){
            lifecycleScope.launch(IO){
                if (viewModel.login(
                    binding.TextEditInputEmailLogin.text.toString().trim(),
                    binding.TextEditInputPasswordLogin.text.toString().trim()
                )){
                    withContext(Main){
                        irParaConta()
                    }
                } else
                    withContext(Main) {
                        Toast.makeText(
                            context,
                            "Houve um erro no login. Verifique suas credenciais.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
            }
        }
    }

    fun irParaConta(){
        findNavController().navigate(R.id.action_loginFragment_to_myAccountFragment)
    }

    fun irParaForgot(v: View){
        findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
    }

    fun irParaSignUp(v: View){
        findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
    }

}