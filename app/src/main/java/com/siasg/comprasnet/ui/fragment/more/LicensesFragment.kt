package com.siasg.comprasnet.ui.fragment.more

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.siasg.comprasnet.databinding.FragmentLicensesBinding


class LicensesFragment : Fragment() {

    private lateinit var binding: FragmentLicensesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLicensesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.fragment = this

        return binding.root
    }

    fun abrirGitHub(v: View){
        val url = "https://github.com/RayanOvelha/Comprasnet-App"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

}