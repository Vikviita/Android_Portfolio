package com.vikvita.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds

class WinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_win)
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        findViewById<TextView>(R.id.textView4).text = intent.getStringExtra("player")
        MobileAds.initialize(this)
        findViewById<AdView>(R.id.adView).loadAd(AdRequest.Builder().build())
        findViewById<TextView>(R.id.button).setOnClickListener {
            finish()
        }
    }
}