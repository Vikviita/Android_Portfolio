package com.udacity.project4.locationreminders.reminderslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.project4.R
import com.udacity.project4.base.BaseRecyclerViewAdapter
import com.udacity.project4.base.NavigationCommand
import com.udacity.project4.databinding.ItReminderBinding
typealias Navigation=(ReminderDataItem)->Unit
// Use data binding to show the reminder on the item
class RemindersListAdapter(val nav:Navigation) :ListAdapter<ReminderDataItem,RemindersListAdapter.ReminderViewHolder>(DiffCallBack()) {
    class ReminderViewHolder private constructor(val binding:ItReminderBinding):RecyclerView.ViewHolder(binding.root){
        fun onBind(item:ReminderDataItem,nav: Navigation){
            binding.item=item
            binding.root.setOnClickListener { nav.invoke(item) }
        }

        companion object{

            fun getView(parent: ViewGroup):ReminderViewHolder{
                val layoutInflater=LayoutInflater.from(parent.context)

                val binding=ItReminderBinding.inflate(layoutInflater,parent,false)


                return ReminderViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder =ReminderViewHolder.getView(parent)

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        holder.onBind(getItem(position),nav)
    }


}

class DiffCallBack:DiffUtil.ItemCallback<ReminderDataItem>(){
    override fun areItemsTheSame(oldItem: ReminderDataItem, newItem: ReminderDataItem): Boolean= oldItem==newItem

    override fun areContentsTheSame(oldItem: ReminderDataItem, newItem: ReminderDataItem): Boolean= oldItem.id==newItem.id

}

