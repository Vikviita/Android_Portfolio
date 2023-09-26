package com.vikvita.fooddelivery.data.remote
import com.vikvita.fooddelivery.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private val retrofit= Retrofit.Builder()
   .addConverterFactory(ScalarsConverterFactory.create())
   .baseUrl(Constants.BASE_URL)
   .build()


interface APIService {
   @GET("search.php")
   suspend fun getList(@Query("f") f:String="a"):String
}


object ApiNetwork{

   fun createService():APIService{

      return retrofit.create(APIService::class.java)
   }

}