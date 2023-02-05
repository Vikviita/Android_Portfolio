package com.vikvita.wheatherapp

import WheatherAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vikvita.wheatherapp.databinding.ActivityMainBinding
import com.vikvita.wheatherapp.models.Wheather

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        fullScreen()
        setRecyclerView()




        }


fun setRecyclerView() {
    val wheatherService = (applicationContext as App).wheatherService
    val adapter = WheatherAdapter()
    adapter.data = wheatherService.getData()
    binding.apply {
        list.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.HORIZONTAL, false)
        list.adapter = adapter
    }
}
    fun fullScreen() {
        with(window) {
            setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

}