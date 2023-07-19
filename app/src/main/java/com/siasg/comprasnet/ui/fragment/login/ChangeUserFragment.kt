package com.siasg.comprasnet.ui.fragment.login

import android.os.Bundle
import android.util.Log
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
import com.siasg.comprasnet.databinding.FragmentChangeUserBinding
import com.siasg.comprasnet.ui.extensions.hideKeyboard
import com.siasg.comprasnet.viewmodel.SupabaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class ChangeUserFragment : Fragment() {

    private lateinit var binding: FragmentChangeUserBinding
    private var TAG: String = "ChangeUser"
    private val viewModel: SupabaseViewModel by viewModels()

    @Inject
    lateinit var client: SupabaseClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeUserBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.fragment = this

        return binding.root
    }

    fun back(v:View){
        findNavController().navigateUp()
    }

    @SuppressWarnings
    fun irParaLogin(v: View) {
        val email = binding.TextEditEmailAddress.text.toString().trim()
        val nome = binding.TextEditChangeName.text.toString().trim()
        val password = binding.TextEditConfirmPassword.text.toString().trim()
        hideKeyboard()

        if (verificaCampos()) {
            lifecycleScope.launch(IO) {
                withContext(Main) {
                    binding.btSendEmailChange.isEnabled = false
                    binding.btCancelEmailChange.isEnabled = false
                }
                if (updateUser(email, nome, password)) {
                    withContext(Main) {
                        viewModel.logout()
                        findNavController().navigate(R.id.action_changeUserFragment_to_loginFragment)
                    }
                }
            }
        }
    }

    private suspend fun updateUser(
        emailInput: String,
        nome: String,
        passwordInput: String
    ): Boolean {

        return kotlin.runCatching {
            val uid = client.gotrue.retrieveUserForCurrentSession().id
            when {
                passwordInput.isNotEmpty() && emailInput.isNotEmpty() && nome.isNotEmpty() -> {
                    Log.i(TAG, "atualizando email, senha e nome")
                    client.gotrue.modifyUser(Email) {
                        email = emailInput
                        password = passwordInput
                    }
                    viewModel.updateCurrentUserName(uid, nome)
                }

                passwordInput.isNotEmpty() && emailInput.isNotEmpty() -> {
                    Log.i(TAG, "atualizando email e senha")
                    client.gotrue.modifyUser(Email) {
                        email = emailInput
                        password = passwordInput
                    }
                }

                passwordInput.isNotEmpty() && emailInput.isNotEmpty() -> {
                    Log.i(TAG, "atualizando senha")
                    client.gotrue.modifyUser(Email) {
                        password = passwordInput
                    }
                }

                nome.isNotEmpty() ->
                    viewModel.updateCurrentUserName(uid, nome)

                else ->
                    withContext(Main) {
                        Toast.makeText(context, "Nenhum dado foi alterado", Toast.LENGTH_SHORT)
                            .show()
                    }
            }
        }.onSuccess {
            withContext(Main) {
                Toast.makeText(
                    context,
                    "Dados alterados com sucesso, verifique seu email e logue novamente para atualizar",
                    Toast.LENGTH_LONG
                ).show()
            }
        }.onFailure {
            withContext(Main) {
                Toast.makeText(context, "Erro, tente novamente mais tarde", Toast.LENGTH_SHORT)
                    .show()
                binding.btSendEmailChange.isEnabled = true
                binding.btCancelEmailChange.isEnabled = true
                it.printStackTrace()
            }
        }.isSuccess

    }

    private fun emailValidator(emailToText: String): Boolean =
        Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()

    fun verificaCampos(): Boolean {
        with(binding) {
            val email = TextEditEmailAddress.text.toString().trim()
            val password1 = TextEditNewPassword.text.toString().trim()
            val password2 = TextEditConfirmPassword.text.toString().trim()

            if (!email.isEmpty() && !emailValidator(email)) {
                Toast.makeText(context, "O email digitado não é válido", Toast.LENGTH_SHORT).show()
                TextEditEmailAddress.requestFocus()
                return false
            }
            if ((!password1.isEmpty() && !password2.isEmpty()) && (password1 != password2)) {
                Toast.makeText(context, "As duas senhas digitadas não conferem", Toast.LENGTH_SHORT)
                    .show()
                TextEditConfirmPassword.requestFocus()
                return false
            }
            if (!email.isEmpty() && password1.isEmpty()) {
                Toast.makeText(
                    context,
                    "Você precisa mudar sua senha, para mudar o email",
                    Toast.LENGTH_LONG
                ).show()
                TextEditNewPassword.requestFocus()
                return false
            }

            return true
        }
    }
}