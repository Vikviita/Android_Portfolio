package com.vikvita.fooddelivery.domain

import com.vikvita.fooddelivery.data.dto.DishItem

interface DataSource {

    suspend fun getList():Array<DishItem>

    suspend fun getListFromDB():Array<DishItem>
    suspend fun insertToDatabase(array:Array<DishItem>)
    suspend fun clearDB()
}