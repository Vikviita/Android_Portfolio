package com.vikvita.testexercise.data.local

import androidx.room.Database

import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vikvita.testexercise.data.dto.BasketItem
import com.vikvita.testexercise.data.dto.DishesItem

@Database(entities = [DishesItem::class,BasketItem::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class Database:RoomDatabase(){
    abstract fun dishesDAO(): DatabaseDAO
}



