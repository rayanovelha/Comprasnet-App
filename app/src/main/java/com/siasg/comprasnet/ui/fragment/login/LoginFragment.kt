package com.siasg.comprasnet.ui.fragment.login

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.siasg.comprasnet.R
import com.siasg.comprasnet.databinding.FragmentLoginBinding
import com.siasg.comprasnet.ui.extensions.hideKeyboard
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

    private lateinit var progressBar: ProgressBar
    private lateinit var buttonLogin: Button
    private lateinit var textForgot: TextView
    private lateinit var textSignUp: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.fragment = this

        progressBar = binding.pbLogoLogin
        buttonLogin = binding.btLogin
        textForgot = binding.tvForgotPassword
        textSignUp = binding.tvLoginSignUp

        val isLogged = viewModel.isLogged()

        if (isLogged) {
            irParaConta()
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
                withContext(Main){
                    hideKeyboard()
                    progressBar.visibility = View.VISIBLE
                    buttonLogin.visibility = View.GONE
                    textForgot.visibility = View.GONE
                    textSignUp.visibility = View.GONE
                }
                if (viewModel.login(
                    binding.TextEditInputEmailLogin.text.toString().trim(),
                    binding.TextEditInputPasswordLogin.text.toString().trim()
                )){
                    withContext(Main){
                        irParaConta()
                    }
                } else
                    withContext(Main) {

                        progressBar.visibility = View.GONE
                        buttonLogin.visibility = View.VISIBLE
                        textForgot.visibility = View.VISIBLE
                        textSignUp.visibility = View.VISIBLE

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