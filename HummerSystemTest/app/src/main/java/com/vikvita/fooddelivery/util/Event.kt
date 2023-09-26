package com.vikvita.fooddelivery.util

class Event<T>(private val value:T) {
    var handled=false


    fun getValue():T?{
        if (handled)return null
        handled=true
        return value
    }
}