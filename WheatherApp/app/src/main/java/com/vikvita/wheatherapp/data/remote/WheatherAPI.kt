package com.vikvita.wheatherapp.data.remote

import com.vikvita.wheatherapp.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


val retrofit= Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(Constants.BASE_URL)
    .build()

interface WheatherService{
    @GET("forecast.json")
    suspend fun getInfo(@Query("key") key:String=Constants.WHEATHER_API_KEY,@Query("q")location:String):String
}



object WheatherNetwork{

    fun getService():WheatherService{
        return retrofit.create(WheatherService::class.java)
    }
}