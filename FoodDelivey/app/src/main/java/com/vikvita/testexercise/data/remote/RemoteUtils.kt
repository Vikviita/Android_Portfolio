package com.vikvita.testexercise.data.remote

import com.vikvita.testexercise.data.dto.DishesItem
import com.vikvita.testexercise.utils.TagsOfDish
import org.json.JSONObject

fun parseDishesJsonResult(jsonResult: JSONObject): Array<DishesItem> {
    val dishesArrayJson = jsonResult.getJSONArray("dishes")

    val DishesList = ArrayList<DishesItem>()

            for (i in 0 until dishesArrayJson.length()) {
                val dishesJson = dishesArrayJson.getJSONObject(i)
                val id = dishesJson.getInt("id")
                val name = dishesJson.getString("name")
                val price = dishesJson.getInt("price")
                val weight= dishesJson.getInt("weight")
                val description = dishesJson.getString("description")
                val pictureUrl = dishesJson.getString("image_url")

                val tegsArray = dishesJson
                    .getJSONArray("tegs")
                val tegsList = mutableListOf<TagsOfDish>()
                for (i in 0 until tegsArray.length()){
                     if(tegsArray.getString(i).equals("Все меню")){
                         tegsList.add(TagsOfDish.DEF)
                     }
                    else if(tegsArray.getString(i).equals("Салаты")){
                        tegsList.add(TagsOfDish.SALAD)
                    }
                     else if(tegsArray.getString(i).equals("С рыбой")){
                         tegsList.add(TagsOfDish.WITH_FISH)
                     }
                     else if(tegsArray.getString(i).equals("С рисом")){
                         tegsList.add(TagsOfDish.WITH_RICE)
                     }
                }
                val dish= DishesItem(
                    id=id,
                    name=name,
                    price = price,
                    weight=weight,
                    description = description,
                    pictureUrl =pictureUrl,
                    tegs = tegsList
                )
                DishesList.add(dish)
            }
    return DishesList.toArray(arrayOf<DishesItem>())
        }




