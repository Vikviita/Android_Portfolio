package com.vikvita.fooddelivery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.vikvita.fooddelivery.databinding.ActivityMainBinding
import com.vikvita.fooddelivery.databinding.ToolbarBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var toolbarBinding: ToolbarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        setWindowSettings()
        setToolbar()


    }



    private fun setToolbar(){
        toolbarBinding=ToolbarBinding.bind(layoutInflater.inflate(R.layout.toolbar,null))
        toolbarBinding.root.layoutParams=android.widget.Toolbar.LayoutParams(android.widget.Toolbar.LayoutParams.MATCH_PARENT, android.widget.Toolbar.LayoutParams.WRAP_CONTENT)
        binding.materialToolbar2.addView(toolbarBinding.root)
        val arratAdapter=ArrayAdapter(
            this,
            R.layout.spinner_item,
            R.id.spinner_text,
            listOf(
                "Москва",
                "Ташкент"
            )
        )

        toolbarBinding.spinnetToolbar.adapter=arratAdapter
    }

    private fun setWindowSettings(){

        window.decorView.apply {

            systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        }
    }
}