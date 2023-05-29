package com.siasg.comprasnet.ui.fragment.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.siasg.comprasnet.R
import com.siasg.comprasnet.databinding.FragmentOnboardingBinding
import com.siasg.comprasnet.ui.adapter.OnboardingAdapter
import com.siasg.comprasnet.ui.fragment.onboarding.screen.OnboardingFirstFragment
import com.siasg.comprasnet.ui.fragment.onboarding.screen.OnboardingSecondFragment
import com.siasg.comprasnet.ui.fragment.onboarding.screen.OnboardingThirdFragment

class OnboardingFragment : Fragment() {

    private lateinit var binding: FragmentOnboardingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.fragment = this

        val listaFragmentos = arrayListOf(
            OnboardingFirstFragment(),
            OnboardingSecondFragment(),
            OnboardingThirdFragment()
        )

        val adaptador = OnboardingAdapter(
            listaFragmentos,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        binding.vpOnboarding.adapter = adaptador
        binding.wormDotsIndicator.attachTo(binding.vpOnboarding)


        return binding.root
    }


    fun start(v: View){
        findNavController().navigate(R.id.action_onboardingFragment3_to_homeFragment)
    }

}
