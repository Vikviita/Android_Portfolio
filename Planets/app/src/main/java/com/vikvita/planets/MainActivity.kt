package com.vikvita.planets

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vikvita.planets.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private var list=ArrayList<Planet>()
    private var image=ArrayList<Int>()
    private var image_info=ArrayList<Int>()
    private var name=ArrayList<String>()
    private var def=ArrayList<String>()
    private var search:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.navigationBarColor=resources.getColor(R.color.black)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        fillArray()
        recView(list)
        binding.apply {
            planets.setOnClickListener {
                startActivity(Intent(this@MainActivity, Planets::class.java))

            }
            searchButton.setOnClickListener{
                val title=searchText.text.toString()
                var check=false
                for(i in name.indices) {
                    if (title.isBlank()) {
                        Toast.makeText(this@MainActivity, "Empty", Toast.LENGTH_SHORT).show()
                        recView(list)
                        break
                    } else if (name[i].lowercase() == title.lowercase()) {
                        recView(arrayListOf(Planet(image[i], name[i], def[i], image_info[i])))
                        check=true
                        break
                    }
                }
                if (!check){
                    Toast.makeText(this@MainActivity,"NOT FOUND",Toast.LENGTH_SHORT).show()
                    recView(list)
            }

            }
        }

    }
    fun fillArray(){
        var imageAll=resources.obtainTypedArray(R.array.image_all)
        val imager=IntArray(imageAll.length())
        for(i in imager.indices){
            image.add(imageAll.getResourceId(i,0))
        }
        var array=resources.getStringArray(R.array.name_arr)
        for(i in imager.indices){
            name.add(array[i])
        }
        array=resources.getStringArray(R.array.def_arr)
        for(i in imager.indices){
            def.add(array[i])
        }
        imageAll=resources.obtainTypedArray(R.array.image_info)
        for(i in imager.indices){
            image_info.add(imageAll.getResourceId(i,0))
        }
        for(i in image.indices){
            list.add(Planet(image[i],name[i],def[i],image_info[i]))
        }
    }
    private fun recView(list:ArrayList<Planet>){
        binding.apply{
            rcView.layoutManager=LinearLayoutManager(this@MainActivity,RecyclerView.HORIZONTAL,false)
            rcView.adapter=Adapter(list,this@MainActivity)

        }
    }
}