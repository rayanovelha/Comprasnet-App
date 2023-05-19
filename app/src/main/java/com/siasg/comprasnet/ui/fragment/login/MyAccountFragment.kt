package com.siasg.comprasnet.ui.fragment.login

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.siasg.comprasnet.databinding.FragmentMyAccountBinding
import com.siasg.comprasnet.domain.PostgrestMessage
import com.siasg.comprasnet.repository.Resource
import com.siasg.comprasnet.viewmodel.SupabaseViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class MyAccountFragment : Fragment() {

    private lateinit var binding: FragmentMyAccountBinding
    private val viewModel: SupabaseViewModel by viewModels()
    private lateinit var uidCopy: String
    private lateinit var alertBuilder: AlertDialog.Builder

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyAccountBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.fragment = this

        alertBuilder = AlertDialog.Builder(context)

        binding.tvUserEmail.isVisible = false
        binding.shimmerDetails.startShimmer()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch(IO) {
            withContext(Main) {
                if (!viewModel.isLogged()) {
                    irParaLogin()
                }

            }
            atualizaDados(viewModel.getCurrentUserInfo(), viewModel.getCurrentUserData())
            withContext(Main) {
                binding.tvUserEmail.isVisible = true
                binding.shimmerDetails.stopShimmer()
                binding.shimmerDetails.isVisible = false
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun atualizaDados(
        resourceUserInfo: LiveData<Resource<UserInfo>>,
        resourceUserData: LiveData<Resource<PostgrestMessage>>
    ) {
        viewLifecycleOwner.lifecycleScope.launch(Main) {
            resourceUserData.observe(viewLifecycleOwner) { resource ->
                resource.dado?.let {
                    val name = it.name
                    var favs = 0
                    if (!it.favorites.isEmpty())
                        favs = it.favorites.split(",").size
                    binding.tvUserEmail.text = "Nome: $name"
                    binding.tvUidAccount.text = "$favs contratos favoritos"
                }
            }
            resourceUserInfo.observe(viewLifecycleOwner) { resource ->
                resource.dado?.let {
                    val email = it.email
                    val uid = it.id
                    uidCopy = it.id
                    binding.tvUserEmail.text = "${binding.tvUserEmail.text}\nEmail: $email"
                    binding.tvUidAccount.text = "UID: $uid\n${binding.tvUidAccount.text}"
                }
            }
        }
    }

    fun logout(v: View) {
        alertBuilder.setMessage("Você tem certeza que deseja sair?")
            .setCancelable(false)
            .setPositiveButton("Sim") { dialog, id ->
                lifecycleScope.launch(Main) {
                    if (viewModel.logout()) {
                        irParaLogin()
                    } else
                        Toast.makeText(
                            requireContext(),
                            "Erro ao sair, tente novamente",
                            Toast.LENGTH_SHORT
                        ).show()
                }
            }
            .setNegativeButton("Não") { dialog, id ->
                dialog.dismiss()
            }
        val alert = alertBuilder.create()
        alert.show()
    }

    fun deletarConta(v: View) {
        alertBuilder.setMessage("Você tem certeza que deseja apagar a conta?")
            .setCancelable(false)
            .setPositiveButton("Sim") { dialog, id ->
                lifecycleScope.launch(Main) {
                    if (viewModel.deleteAccount()) {
                        irParaLogin()
                    } else
                        Toast.makeText(
                            requireContext(),
                            "Erro ao deletar conta, tente novamente",
                            Toast.LENGTH_SHORT
                        ).show()
                }
            }
            .setNegativeButton("Não") { dialog, id ->
                dialog.dismiss()
            }
        val alert = alertBuilder.create()
        alert.show()
    }

    fun alterarDados(v: View) {
        irParaForgotPassword()
    }

    fun clicarUid(v: View) {
        val clipboard =
            requireContext().getSystemService(android.content.Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
        val clip = android.content.ClipData.newPlainText("UID", uidCopy)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(context, "UID copiado para a área de transferência", Toast.LENGTH_SHORT)
            .show()
    }

    private fun irParaLogin() {
        val action = MyAccountFragmentDirections.actionMyAccountFragmentToLoginFragment()
        findNavController().navigate(action)
    }

    fun irParaForgotPassword() {
        val action = MyAccountFragmentDirections.actionMyAccountFragmentToForgotPasswordFragment()
        binding.root.findNavController().navigate(action)
    }
}