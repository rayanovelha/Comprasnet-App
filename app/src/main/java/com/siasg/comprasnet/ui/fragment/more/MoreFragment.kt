package com.siasg.comprasnet.ui.fragment.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.siasg.comprasnet.R
import com.siasg.comprasnet.databinding.FragmentMoreBinding


class MoreFragment : Fragment() {

    private lateinit var binding: FragmentMoreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoreBinding.inflate(inflater, container, false)
        binding.fragment = this
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    fun irParaAjuda(v: View){
        findNavController().navigate(R.id.action_moreFragment_to_helpFragment)
    }

    fun irParaLicenca(v: View){
        findNavController().navigate(R.id.action_moreFragment_to_licensesFragment)
    }

    fun irParaFavorites(v:View){
        findNavController().navigate(R.id.action_moreFragment_to_favoritesFragment)
    }

}