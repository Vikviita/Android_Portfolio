package com.vikvita.wheatherapp.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

object LocalDB {


    fun getDatabaseDao(context: Context):DatabaseDAO{
        return Room.databaseBuilder(
            context.applicationContext,
            Database::class.java,
            "wheather.db"
        ).build().databaseDao()

    }
}