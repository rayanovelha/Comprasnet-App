package com.siasg.comprasnet.ui.fragment.onboarding

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.siasg.comprasnet.R
import com.siasg.comprasnet.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.gotrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    @Inject
    lateinit var client: SupabaseClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.fragment = this

        val currentUser = client.gotrue.currentSessionOrNull()

        lifecycleScope.launch (Dispatchers.Main){
            delay(2000)
            if (currentUser != null) {
                Log.i("Splash Fragment","currentUser != null")
                startMainActivity()
            } else {
                Log.i("Splash Fragment","currentUser == null")
                startOnboarding()
            }
        }

        return binding.root
    }


    private fun startOnboarding() {
        findNavController().navigate(R.id.action_splashFragment_to_onboardingFragment)
    }

    private fun startMainActivity() {
        findNavController().navigate(R.id.action_splashFragment_to_mainActivity)
    }

}