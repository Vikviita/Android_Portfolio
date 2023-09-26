package com.vikvita.testexercise.screens.mainScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vikvita.testexercise.databinding.MainCategoryItemBinding
typealias NavFunc=(title:String)->Unit
class MainAdapter(val list:List<MainCategoryItem>, val nav:NavFunc):RecyclerView.Adapter<MainAdapter.MyViewHolder>(){


    class MyViewHolder private constructor(val binding:MainCategoryItemBinding):RecyclerView.ViewHolder(binding.root){
    fun bind(item:MainCategoryItem,nav:NavFunc){

        binding.categoryPic.setImageResource(item.pictureId)
        binding.categoryText.text= item.text
binding.root.setCardBackgroundColor(item.color)
        binding.root.setOnClickListener{
            nav.invoke(item.text)
        }


    }

    companion object{
        fun getViewHolder(parent:ViewGroup):MyViewHolder{
            val layoutInflater=LayoutInflater.from(parent.context)
            val binding=MainCategoryItemBinding.inflate(layoutInflater,parent,false)
            return MyViewHolder(binding)
        }
    }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =MyViewHolder.getViewHolder(parent)


    override fun getItemCount(): Int =list.size


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position],nav)
    }


}