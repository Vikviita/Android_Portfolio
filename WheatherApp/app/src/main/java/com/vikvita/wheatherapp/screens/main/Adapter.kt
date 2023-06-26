package com.vikvita.wheatherapp.screens.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintSet.Layout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.vikvita.wheatherapp.data.dto.WheatherItem
import com.vikvita.wheatherapp.databinding.ActivityMainBinding
import com.vikvita.wheatherapp.databinding.WheatherBinding


typealias Action=(item:WheatherItem)->Unit

class Adapter(private val action:Action):ListAdapter<WheatherItem,Adapter.MyViewHolder>(Diff()) {

    class MyViewHolder private constructor(val binding: WheatherBinding):RecyclerView.ViewHolder(binding.root){

       fun bind(item: WheatherItem,action: Action){
           val rainRate="${item.rainRate}%"
           binding.rainRate.text=rainRate
           binding.time.text=item.time
           val temp="${item.currentTemp}°"
           binding.temp.text=temp
           binding.WhImage.setImageResource(item.picture)

           binding.root.setOnClickListener{
               action.invoke(item)
           }

       }

        companion object{

            fun getViewHolder(parent: ViewGroup):MyViewHolder{
                val layoutInflater=LayoutInflater.from(parent.context)
                val binding=WheatherBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(binding)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder.getViewHolder(parent)

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.bind(getItem(position),action)
    }
}


class Diff:DiffUtil.ItemCallback<WheatherItem>(){
    override fun areItemsTheSame(oldItem: WheatherItem, newItem: WheatherItem): Boolean = oldItem==newItem

    override fun areContentsTheSame(oldItem: WheatherItem, newItem: WheatherItem): Boolean = oldItem==newItem

}