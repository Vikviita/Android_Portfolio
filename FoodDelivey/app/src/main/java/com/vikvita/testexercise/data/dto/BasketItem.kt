package com.vikvita.testexercise.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "basket_table")
data class BasketItem(
    @PrimaryKey
    val id:Int,
    val pictureUrl:String,
    val text:String,
    val weight:Int,
    val price:Int,
    var amount:Int=0
){

    fun incrementAmount(){
      amount++
    }
    fun decrementAmount(){
        amount--
    }
}