package com.siasg.comprasnet.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.siasg.comprasnet.R
import com.siasg.comprasnet.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    var filter: Int = 0
    var search: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.fragment = this
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    fun pesquisar(v: View) {
        search = binding.editTextSearch.text.toString().trim()
        filter = 10
        val action = HomeFragmentDirections.actionHomeFragmentToResultFragment(filter, search)
        binding.root.findNavController().navigate(action)
    }

    fun irParaResultTotal(v: View) {

        filter = 0
        val action = HomeFragmentDirections.actionHomeFragmentToResultFragment(filter, search)
        binding.root.findNavController().navigate(action)

    }

    fun irParaResult30(v: View) {

        filter = 30
        val action = HomeFragmentDirections.actionHomeFragmentToResultFragment(filter, search)
        binding.root.findNavController().navigate(action)

    }

    fun irParaResult60(v: View) {

        filter = 60
        val action = HomeFragmentDirections.actionHomeFragmentToResultFragment(filter, search)
        binding.root.findNavController().navigate(action)

    }

    fun irParaResult90(v: View) {

        filter = 90
        val action = HomeFragmentDirections.actionHomeFragmentToResultFragment(filter, search)
        binding.root.findNavController().navigate(action)

    }

    fun irParaResult180(v: View) {

        filter = 180
        val action = HomeFragmentDirections.actionHomeFragmentToResultFragment(filter, search)
        binding.root.findNavController().navigate(action)

    }

    fun irParaResult180mais(v: View) {

        filter = 181
        val action = HomeFragmentDirections.actionHomeFragmentToResultFragment(filter, search)
        binding.root.findNavController().navigate(action)

    }

}