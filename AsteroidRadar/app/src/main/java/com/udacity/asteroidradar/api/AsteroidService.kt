package com.udacity.asteroidradar.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.APIKEY
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.databse.AsteroidDB
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit





private val moshi=Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()




private val retrofit=Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()



interface AsteroidService{
    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(@Query("api_key") apiKey:String):String
    @GET("planetary/apod")
    suspend fun getPictureOfDay(@Query("api_key") apiKey:String= APIKEY):PictureOfDay
}


object AsteroidNetwork{
    val retrofitService:AsteroidService by lazy{ retrofit.create(AsteroidService::class.java) }
}