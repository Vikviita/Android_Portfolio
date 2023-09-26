package com.vikvita.fooddelivery.data.remote

import com.vikvita.fooddelivery.data.dto.DishItem
import org.json.JSONObject


fun parseJSON(json:JSONObject):Array<DishItem>{
    val mealArray=json.getJSONArray("meals")
        val array=ArrayList<DishItem>()
    for(i in 0 until mealArray.length()){
        val jsonObject=mealArray.getJSONObject(i)

        val id = jsonObject.getString("idMeal")
        val name=jsonObject.getString("strMeal")
        val pictureUrl=jsonObject.getString("strMealThumb")
        val info=jsonObject.getString("strInstructions")

        array.add(
            DishItem(
            id=id,
            name=name,
            pictureUrl = pictureUrl,
            info = info
        ))
    }

    return array.toArray(arrayOf<DishItem>())

}