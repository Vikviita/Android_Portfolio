package com.vikvita.testexercise.data.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.vikvita.testexercise.data.dto.DishesItem
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.create
import retrofit2.http.GET


private val moshi= Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl("https://run.mocky.io/")
    .build()




interface RemoteService {
    @GET("v3/aba7ecaa-0a70-453b-b62d-0e326c859b3b")
   suspend fun getDishLIst():String
}


object RemoteNetwork {

    fun createService(): RemoteService {


        return retrofit.create(RemoteService::class.java)

    }
}