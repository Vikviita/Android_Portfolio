package com.vikvita.testexercise.screens.categoryScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.vikvita.testexercise.R
import com.vikvita.testexercise.data.dto.DishesItem
import com.vikvita.testexercise.databinding.AllCategoryItemBinding

typealias ShowConfirmActionBar=(DishesItem)->Unit
class CategoryAdapter(val showAction:ShowConfirmActionBar):ListAdapter<DishesItem,CategoryAdapter.MyViewHolder>(DiffCall()){


    class MyViewHolder private constructor(val binding: AllCategoryItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: DishesItem,showAction: ShowConfirmActionBar){


            binding.allText.text=item.name

           binding.root.setOnClickListener{ showAction.invoke(item)}

            Glide.with(binding.allPic.context)
                .load(item.pictureUrl)
                .fitCenter()
                .placeholder(R.drawable.baseline_cloud_download_24)
                .error(R.drawable.baseline_cloud_download_24)
                .into(binding.allPic)

        }

        companion object{
            fun getViewHolder(parent: ViewGroup):MyViewHolder{
                val layoutInflater= LayoutInflater.from(parent.context)
                val binding= AllCategoryItemBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.getViewHolder(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position),showAction)
    }


}


class DiffCall:DiffUtil.ItemCallback<DishesItem>(){
    override fun areItemsTheSame(oldItem: DishesItem, newItem: DishesItem): Boolean = oldItem==newItem


    override fun areContentsTheSame(oldItem: DishesItem, newItem: DishesItem): Boolean = oldItem==newItem
}