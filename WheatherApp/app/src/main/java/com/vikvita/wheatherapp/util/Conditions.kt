package com.vikvita.wheatherapp.util

import com.vikvita.wheatherapp.R

enum class Conditions(val code:Int,val picture:Int){
    SUNNY(
            1000,R.drawable.icon_sunny
    )
    ,CLOUDY(1006,R.drawable.icon_cloudy)
    ,
    SNOW(
      1213,R.drawable.icon_snow
    )
    ,RAIN(
        1183,R.drawable.icon_rain
    )
    ,HEAVY_RAIN(
        1195
    ,R.drawable.icon_heavy_rain
    )
}