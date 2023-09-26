package com.vikvita.fooddelivery.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.vikvita.fooddelivery.R
import com.vikvita.fooddelivery.databinding.FragmentMenuBinding
import org.koin.android.ext.android.inject

class MenuFragment:Fragment() {
    private lateinit var binding:FragmentMenuBinding
    private val viewModel:MenurFragmentViewModel by inject()
    private lateinit var adapter:MenuFragmentAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentMenuBinding.inflate(layoutInflater,container,false)
        setChipListener()
        setAdapter()

        viewModel.showToasts.observe(viewLifecycleOwner){
           val result=it.getValue()
            if(it==null) {
            }
            else {
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.list.observe(viewLifecycleOwner){
            adapter.submitList(it.toList())
        }



        return binding.root
    }






    fun setChipListener(){
       binding.chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
           when(checkedIds[0]){
               R.id.pizza->viewModel.showToast("Пицца")
               R.id.combo->viewModel.showToast("Комбo")
               R.id.deserts->viewModel.showToast("Десерты")
               R.id.drinks->viewModel.showToast("Напитки")
           }


       }


    }


    fun setAdapter(){
        val layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        adapter=MenuFragmentAdapter()
        binding.menuList.layoutManager=layoutManager
        binding.menuList.adapter=adapter
    }
}