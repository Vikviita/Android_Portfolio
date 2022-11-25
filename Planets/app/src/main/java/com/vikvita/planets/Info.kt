package com.vikvita.planets

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.vikvita.planets.databinding.ActivityInfoBinding

class Info : AppCompatActivity() {
    private lateinit var binding:ActivityInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        BottomSheetBehavior.from(binding.sheet).apply {
            peekHeight=250
            this.state=BottomSheetBehavior.STATE_COLLAPSED
        }


        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.chevron_left)
 binding.imageView.setImageResource(intent.getIntExtra("image",R.drawable.sun_info))
var title=intent.getStringExtra("title")?.lowercase()
        binding.web.setBackgroundColor(Color.parseColor("#121212"))
        binding.web.loadUrl("file:///android_asset/$title.html")
    }

    override fun onResume() {
        super.onResume()
window.decorView.systemUiVisibility=  View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    window.navigationBarColor=resources.getColor(R.color.black)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        }
        return true
    }
}