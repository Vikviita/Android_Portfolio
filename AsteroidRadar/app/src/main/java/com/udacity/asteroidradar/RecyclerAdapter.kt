package com.udacity.asteroidradar

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.databinding.AsteroidItemViewBinding
typealias AsteroidListener=(Asteroid)->(Unit)
class RecyclerAdapter(val asteroidListener:AsteroidListener): ListAdapter<Asteroid, RecyclerAdapter.VHolder>(DiffUtillCallBack()) {


    class VHolder(val binding:AsteroidItemViewBinding):RecyclerView.ViewHolder(binding.root){
     fun bind(asteroid:Asteroid,asteroidListener:AsteroidListener){
           binding.root.setOnClickListener {
               asteroidListener.invoke(asteroid)
           }
         binding.asteroid=asteroid
         binding.executePendingBindings()

     }
        companion object{
            fun from(parent: ViewGroup):VHolder{

                return VHolder(AsteroidItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHolder {
        return VHolder.from(parent)
    }

    override fun onBindViewHolder(holder: VHolder, position: Int) {
       holder.bind(getItem(position),asteroidListener)
    }
}


class DiffUtillCallBack:DiffUtil.ItemCallback<Asteroid>(){
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
       return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
       return oldItem==newItem
    }

}