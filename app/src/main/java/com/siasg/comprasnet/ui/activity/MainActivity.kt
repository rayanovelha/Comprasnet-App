package com.siasg.comprasnet.ui.activity

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.siasg.comprasnet.R
import com.siasg.comprasnet.databinding.ActivityMainBinding
import com.siasg.comprasnet.di.ComprasApiService
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var comprasApiService: ComprasApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNavigationView: BottomNavigationView = binding.barNavigationView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragNavHost) as NavHostFragment
        val navController = navHostFragment.navController

        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.itemIconTintList = null

        // INICIAR ONBOARDING
        getSharedPreferences(
            getString(R.string.app_shared_prefs),
            Context.MODE_PRIVATE
        )?.let { sharedPreferences ->
            val isFirstTimeOpening =
                sharedPreferences.getBoolean(getString(R.string.first_time_opening), true)

            if (isFirstTimeOpening) {
                navController.navigate(R.id.onboardingFragment)
                with(sharedPreferences.edit()) {
                    putBoolean(getString(R.string.first_time_opening), false)
                    apply()
                }
            }
        }

        // NAVEGAR FRAGMENTOS
        bottomNavigationView.setOnItemSelectedListener { item ->
            navController.navigate(item.itemId)
            true
        }
        bottomNavigationView.setOnItemReselectedListener {
            navController.navigate(it.itemId)
        }

        // ESCONDER NAVIGATION BAR
        navController.addOnDestinationChangedListener { _, nd, _ ->
            bottomNavigationView.visibility =
                if (nd.id == R.id.onboardingFragment || nd.id == R.id.detailsFragment || nd.id == R.id.forgotPasswordFragment)
                    View.GONE
                else
                    View.VISIBLE
        }
    }
}
