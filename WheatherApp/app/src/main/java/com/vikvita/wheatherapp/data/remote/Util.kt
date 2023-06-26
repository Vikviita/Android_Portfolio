package com.vikvita.wheatherapp.data.remote

import com.vikvita.wheatherapp.data.dto.WheatherCommonInfo
import com.vikvita.wheatherapp.data.dto.WheatherItem
import com.vikvita.wheatherapp.util.Conditions
import org.json.JSONObject

fun parseJsonWheatherItem(jsonObject: JSONObject):Array<WheatherItem>{
    val locationJson=jsonObject.getJSONObject("location")
    val locName=locationJson.getString("name")
    val date=locationJson.getString("localtime").take(9)


    val jsonArray=jsonObject.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(0).getJSONArray("hour")

    val arrayWhether= ArrayList<WheatherItem>()
    for (i in 0 until jsonArray.length()){
        val jsonItem=jsonArray.getJSONObject(i)
        val time=jsonItem.getString("time")
        val temp = jsonItem.getDouble("temp_c")
        val rainRate= jsonItem.getDouble("chance_of_rain")
        val codeCondition= jsonItem.getJSONObject("condition").getInt("code")
        var pictureurl=Conditions.SUNNY.picture
        if(codeCondition==Conditions.CLOUDY.code) pictureurl = Conditions.CLOUDY.picture
        if(codeCondition==Conditions.RAIN.code) pictureurl = Conditions.RAIN.picture
        if(codeCondition==Conditions.HEAVY_RAIN.code) pictureurl = Conditions.HEAVY_RAIN.picture
        if(codeCondition==Conditions.SNOW.code) pictureurl = Conditions.SNOW.picture

        arrayWhether.add(
            WheatherItem(
                currentTemp = temp,
                time = time.removeRange(0..10),
                picture = pictureurl,
                rainRate = rainRate,
                location = locName,
                date = date
            )
        )

    }



return arrayWhether.toArray(arrayOf<WheatherItem>())
}



fun parseJsonWheatherInfo(jsonObject:JSONObject): WheatherCommonInfo{
    val locationJson=jsonObject.getJSONObject("location")
    val locName=locationJson.getString("name")
    val date=locationJson.getString("localtime").take(9)
    val avgTemp=jsonObject.getJSONObject("current").getDouble("temp_c")


    return WheatherCommonInfo(
        location = locName,
        avgTemp = avgTemp,
        date = date
    )

}


