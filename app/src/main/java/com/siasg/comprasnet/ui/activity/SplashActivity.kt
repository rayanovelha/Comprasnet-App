package com.siasg.comprasnet.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.siasg.comprasnet.R
import com.siasg.comprasnet.databinding.ActivitySplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        binding = ActivitySplashBinding.inflate(layoutInflater)

    }

}