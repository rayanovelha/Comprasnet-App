package com.siasg.comprasnet.ui.fragment.login

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.siasg.comprasnet.R
import com.siasg.comprasnet.databinding.FragmentSignUpBinding
import com.siasg.comprasnet.viewmodel.SupabaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SignUpFragment() : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val TAG: String = "SignUP"
    private val viewModel: SupabaseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.fragment = this


        return binding.root
    }

    fun irParaLogin(v: View) {
        if (verificaCampos()) {
            lifecycleScope.launch(Main) {
                if (viewModel.createAccount(
                        binding.TextEditInputEmail.text.toString(),
                        binding.TextEditInputPassword.text.toString(),
                        binding.TextEditInputName.text.toString()
                    )
                ) {
                    Toast.makeText(context, "Conta criada com sucesso", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_signUpFragment_to_loginFragment)
                } else {
                    Toast.makeText(context, "Erro ao criar conta", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun verificaCampos(): Boolean {
        val nome: String = binding.TextEditInputName.text.toString().trim()
        val email: String = binding.TextEditInputEmail.text.toString().trim()
        val password1: String = binding.TextEditInputPassword.text.toString().trim()
        val password2: String = binding.TextEditInputPassword2.text.toString().trim()

        if (email.isEmpty() || nome.isEmpty() || password1.isEmpty() || password2.isEmpty()) {
            Toast.makeText(context, "Alguns dos campos se encontra vazio", Toast.LENGTH_SHORT).show()
        } else if (!emailValidator(email)) {
            Toast.makeText(context, "Email inválido", Toast.LENGTH_SHORT).show()
        } else if (!password1.equals(password2)) {
            Toast.makeText(context, "As senhas não conferem, digite novamente", Toast.LENGTH_SHORT)
                .show()
        } else {
            return true
        }

        return false
    }

    private fun emailValidator(emailToText: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()

}