package com.vikvita.wheatherapp.screens.description

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.vikvita.wheatherapp.data.dto.WheatherItem
import com.vikvita.wheatherapp.databinding.FragmentDescriptionBinding

class DescriptionFragment:Fragment() {
    private lateinit var binding:FragmentDescriptionBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentDescriptionBinding.inflate(inflater,container,false)

        val item=requireArguments().getSerializable("selectedWheatherItem") as WheatherItem

        binding.rainText.text=item.rainRate.toString()
        binding.tempText.text=item.currentTemp.toString()
       binding.imageCondition.setImageResource(item.picture)
        binding.locationText.text=item.location
        val date="${item.date} ${item.time}"
        binding.dateText.text=date




        return binding.root
    }

}