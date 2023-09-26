package com.vikvita.fooddelivery.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vikvita.fooddelivery.data.dto.DishItem

@Database(entities = [DishItem::class], version = 1, exportSchema = false)
abstract class Database:RoomDatabase() {
    abstract fun databaseDAO():DatabaseDAO
}