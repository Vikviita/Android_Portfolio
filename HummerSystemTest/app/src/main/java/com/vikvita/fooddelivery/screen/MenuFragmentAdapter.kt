package com.vikvita.fooddelivery.screen

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vikvita.fooddelivery.R
import com.vikvita.fooddelivery.data.dto.DishItem
import com.vikvita.fooddelivery.databinding.FoodItemBinding
import com.vikvita.fooddelivery.databinding.FragmentMenuBinding

class MenuFragmentAdapter :androidx.recyclerview.widget.ListAdapter<DishItem,MenuFragmentAdapter.MyOwnViewHolder>(DiffUtills()){

    class MyOwnViewHolder private constructor(val binding:FoodItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(item:DishItem){
            binding.foodName.text=item.name
            binding.foodInfo.text=item.info
            val text="от ${item.price} р"
            binding.priceButton.text=text

            Glide.with(binding.foodImage.context)
                .load(item.pictureUrl)
                .placeholder(R.drawable.baseline_cloud_download_24)
                .error(R.drawable.baseline_cloud_download_24)
                .into(binding.foodImage)




        }


        companion object{
            fun getViewHolder(parent:ViewGroup):MyOwnViewHolder{
                val layoutInflater=LayoutInflater.from(parent.context)
                val binding=FoodItemBinding.inflate(layoutInflater,parent,false)
                return MyOwnViewHolder(binding)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyOwnViewHolder {
       return MyOwnViewHolder.getViewHolder(parent)
    }

    override fun onBindViewHolder(holder: MyOwnViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


class DiffUtills:DiffUtil.ItemCallback<DishItem>(){
    override fun areItemsTheSame(oldItem: DishItem, newItem: DishItem): Boolean = oldItem==newItem

    override fun areContentsTheSame(oldItem: DishItem, newItem: DishItem): Boolean= oldItem==newItem

}