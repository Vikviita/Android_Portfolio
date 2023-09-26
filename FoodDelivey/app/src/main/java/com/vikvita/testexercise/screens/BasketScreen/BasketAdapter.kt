package com.vikvita.testexercise.screens.BasketScreen

import android.content.Context
import android.content.res.Resources
import com.vikvita.testexercise.data.dto.BasketItem
import com.vikvita.testexercise.databinding.BasketItemBinding



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vikvita.testexercise.R
import com.vikvita.testexercise.data.dto.DishesItem
import com.vikvita.testexercise.databinding.AllCategoryItemBinding

interface AddAndDelete{
    fun add(newItem:BasketItem)
    fun delete(id:Int)
}
class BasketAdapter(val action:AddAndDelete):ListAdapter<BasketItem,BasketAdapter.MyViewHolder>(DiffCall()){


    class MyViewHolder private constructor(val binding: BasketItemBinding,val resoursec:Resources): RecyclerView.ViewHolder(binding.root){
        fun bind(item: BasketItem,action: AddAndDelete){


            binding.basketNameOfDish.text=item.text
            val price="${item.price}${resoursec.getString(R.string.ruble)}"
            binding.basketPrice.text=price
            val weight="${item.weight}г"
           binding .basketWeight.text=weight

            binding.basketAmount.text=item.amount.toString()
           binding.basketAdd.setOnClickListener {
               action.add(item)
               binding.basketAmount.text=item.amount.toString()
           }
            binding.basketDelete.setOnClickListener {

                action.delete(item.id)
                binding.basketAmount.text=item.amount.toString()
            }

            Glide.with(binding.picBasket.context)
                .load(item.pictureUrl)
                .placeholder(R.drawable.baseline_cloud_download_24)
                .error(R.drawable.baseline_cloud_download_24)
                .into(binding.picBasket)

        }

        companion object{
            fun getViewHolder(parent: ViewGroup):MyViewHolder{
                val layoutInflater= LayoutInflater.from(parent.context)

                val binding= BasketItemBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(binding,parent.resources)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.getViewHolder(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position),action)
    }


}


class DiffCall:DiffUtil.ItemCallback<BasketItem>(){
    override fun areItemsTheSame(oldItem: BasketItem, newItem: BasketItem): Boolean = oldItem==newItem


    override fun areContentsTheSame(oldItem: BasketItem, newItem: BasketItem): Boolean = oldItem.amount==newItem.amount
}