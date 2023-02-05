package com.vikvita.wheatherapp

import android.app.Application
import com.vikvita.wheatherapp.models.WheatherService

class App:Application() {
    val wheatherService=WheatherService()
}