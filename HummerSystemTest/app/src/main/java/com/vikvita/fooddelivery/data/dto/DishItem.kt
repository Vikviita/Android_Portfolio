package com.vikvita.fooddelivery.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
@Entity(tableName = "dish_table")
data class DishItem (
        @PrimaryKey
        val id:String,
        val name:String,
        val pictureUrl:String,
        val info:String,
        val price:Int=(200..1000).random()
        )

