package com.vikvita.wheatherapp.screens.main

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.model.LatLng
import com.vikvita.wheatherapp.R
import com.vikvita.wheatherapp.data.dto.WheatherItem
import com.vikvita.wheatherapp.databinding.FragmentMainBinding
import org.koin.android.ext.android.inject

class MainFragment:Fragment() {
    private lateinit var binding:FragmentMainBinding
    private lateinit var adapter:Adapter
    private val viewModel:MainFragmentViewModel by inject()
    override fun onAttach(context: Context) {
        super.onAttach(context)
       val prefs=requireActivity().getPreferences(Context.MODE_PRIVATE)
        val latlng= LatLng(prefs.getFloat("latitude",0f).toDouble(),prefs.getFloat("longitude",0f).toDouble())
        viewModel.start(latlng)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding= FragmentMainBinding.inflate(inflater,container,false)
        setAdapter()
        viewModel.list.observe(viewLifecycleOwner){


            if(it.isEmpty()){
                binding.progressBar.visibility=View.VISIBLE
            }
            else{
                binding.progressBar.visibility=View.GONE
                adapter.submitList(it)
            }

        }
        viewModel.info.observe(viewLifecycleOwner){
            binding.city.text =it?.location
            val temp="${it?.avgTemp.toString()}°"
            binding.temperature.text=temp
            binding.todayDate.text=it?.date
        }
       Log.d("my","sdfdsdf")
        return binding.root
    }


    fun setAdapter(){
        adapter=Adapter{
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToDescriptionFragment(it))
        }
        val linearLayoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        binding.list.adapter=adapter
        binding.list.layoutManager=linearLayoutManager
    }




}