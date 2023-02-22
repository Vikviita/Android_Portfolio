package com.vikvita.wheatherapp.models

import com.vikvita.wheatherapp.R

class WheatherService {
    private var data = mutableListOf<Wheather>(
        Wheather("Sun", R.drawable.icon_snow, "15%", "23°"),
        Wheather("Mon", R.drawable.icon_sunny, "15%", "23°"),
        Wheather("Tue", R.drawable.icon_cloudy, "15%", "23°"),
        Wheather("Wed", R.drawable.icon_rain, "15%", "23°"),
        Wheather("Thu", R.drawable.icon_heavy_rain, "15%", "23°")

        )
    fun getData()=data


}