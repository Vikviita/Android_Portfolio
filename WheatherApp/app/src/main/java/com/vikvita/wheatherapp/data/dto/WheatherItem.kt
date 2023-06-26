package com.vikvita.wheatherapp.data.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "item_table")
data class WheatherItem(
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null,
    val time:String,
    val currentTemp:Double,
    val picture:Int,
    val rainRate:Double,
    val location:String,
    val date:String


):Serializable

@Entity(tableName = "info_table")
data class WheatherCommonInfo(
    @PrimaryKey(autoGenerate = true)
    var id:Int?=null,
    val location:String,
    val avgTemp:Double,
    val date:String,
)