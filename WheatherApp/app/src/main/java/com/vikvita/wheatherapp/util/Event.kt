package com.vikvita.wheatherapp.util

class Event<T>(private val data:T) {
    var handled=false


    fun getData():T?{
        if(handled) return null
        handled=true
        return data
    }


}