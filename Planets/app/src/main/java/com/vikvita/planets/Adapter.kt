package com.vikvita.planets

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vikvita.planets.databinding.PatternAllBinding
import com.vikvita.planets.databinding.PatternPlanetsBinding
import java.util.ArrayList

open class Adapter(val list:ArrayList<Planet>, private val context: Context):RecyclerView.Adapter<Adapter.Holder>() {
    class Holder(view: View):RecyclerView.ViewHolder(view){
        var binding=PatternAllBinding.bind(view)
        fun bind(planet:Planet,context: Context){
            binding.apply{
                imageType.setImageResource(planet.image)
            name.text=planet.name
            def.text=planet.def
                imageButton2.setOnClickListener{
                    val intent=Intent(context,Info::class.java).apply {
                        putExtra("image",planet.image_info)
                        putExtra("title",planet.name)
                    }
                    context.startActivity(intent)
                }
                imageType.setOnClickListener {
                    val intent=Intent(context,Info::class.java).apply {
                        putExtra("image",planet.image_info)
                        putExtra("title",planet.name)
                    }
                    context.startActivity(intent)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view=LayoutInflater.from(context).inflate(R.layout.pattern_all,parent,false)
        return Holder(view)
    }

    override fun getItemCount(): Int= list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(list[position],context)
    }

}