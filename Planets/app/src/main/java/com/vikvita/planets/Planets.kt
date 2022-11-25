package com.vikvita.planets

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vikvita.planets.databinding.ActivityMainBinding
import com.vikvita.planets.databinding.ActivityPlanetsBinding

class Planets : AppCompatActivity() {
    private lateinit var binding: ActivityPlanetsBinding
    private var list=ArrayList<Planet>()
    private var image=ArrayList<Int>()
    private var image_info=ArrayList<Int>()
    private var name=ArrayList<String>()
    private var def=ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityPlanetsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.navigationBarColor=resources.getColor(R.color.black)
        supportActionBar?.title= Html.fromHtml("<p><big>Planets</big></p>",Html.FROM_HTML_MODE_COMPACT)
   fillArray()
        recView(list)
    }


    fun fillArray(){
        var imageAll=resources.obtainTypedArray(R.array.image_arr)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.back,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.back->{
                startActivity(
                    Intent(this,MainActivity::class.java)
                )
                finish()
            }

        }
        return true
    }

    private fun recView(list:ArrayList<Planet>) {
        binding.apply {
            rcView.layoutManager = GridLayoutManager(this@Planets, 2)
            rcView.adapter = object:Adapter(list,this@Planets){
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
                    val view=LayoutInflater.from(parent.context).inflate(R.layout.pattern_planets,parent,false)
                    return Holder(view)
                }
            }
            }

        }

}