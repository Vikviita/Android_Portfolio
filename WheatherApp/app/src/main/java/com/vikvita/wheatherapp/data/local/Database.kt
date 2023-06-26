package com.vikvita.wheatherapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vikvita.wheatherapp.data.dto.WheatherCommonInfo
import com.vikvita.wheatherapp.data.dto.WheatherItem

@Database(entities =[WheatherItem::class ,WheatherCommonInfo::class], version = 1, exportSchema = false)
abstract class Database :RoomDatabase(){
    abstract fun databaseDao():DatabaseDAO

}