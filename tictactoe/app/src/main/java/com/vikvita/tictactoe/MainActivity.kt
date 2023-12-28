package com.vikvita.tictactoe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewConfiguration
import android.view.WindowManager
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.vikvita.tictactoe.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

        binding.ticButton.setOnClickListener {
            start(it, ticTac.TIC)
        }
        binding.tacButton.setOnClickListener {
            start(it, ticTac.TAC)
        }

        MobileAds.initialize(this)
        binding.adView.loadAd(AdRequest.Builder().build())


    }

    fun start(view: View, ticTac: ticTac) {
        intent = Intent(this@MainActivity, MainActivity2::class.java).putExtra("type", ticTac)
        startActivity(intent)
    }

}