package com.siasg.comprasnet.ui.activity

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.siasg.comprasnet.R
import com.siasg.comprasnet.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.barNavigationView)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragNavHost) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        setupWithNavController(bottomNavigationView, navController)

        bottomNavigationView.itemIconTintList = null

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.loginFragment -> {
                    navController.navigate(R.id.loginFragment)
                    true
                }
                R.id.favoritesFragment -> {
                    navController.navigate(R.id.favoritesFragment)
                    true
                }
                R.id.moreFragment -> {
                    navController.navigate(R.id.moreFragment)
                    true
                }
                else -> false
            }
        }

        val data: Uri? = intent.data
        if (data != null) {
            val contratoId: String? = data.lastPathSegment
            if (contratoId != null) {
                val idArgs = contratoId.toInt()
                navController.navigate(R.id.detailsFragment, Bundle().apply {
                    putInt("idArgs",idArgs)
                })
            }
        }

    }

}
