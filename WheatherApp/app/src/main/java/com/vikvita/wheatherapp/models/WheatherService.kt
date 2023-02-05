package com.vikvita.wheatherapp.models

import com.vikvita.wheatherapp.R

class WheatherService {
private var data = (1..24).map {
    Wheather("12 Am",R.drawable.baseline_ac_unit_24,15,23) }.toMutableList()
    fun getData()=data


}