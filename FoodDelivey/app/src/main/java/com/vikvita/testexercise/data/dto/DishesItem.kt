package com.vikvita.testexercise.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.vikvita.testexercise.utils.TagsOfDish

@Entity("dishes_table")
data class DishesItem(
                     @PrimaryKey
                     val id:Int,
                      val name:String,
                      val price:Int,
                      val weight:Int,
                      val description:String,
                     val pictureUrl:String,
                     val tegs:List<TagsOfDish>
                   )



