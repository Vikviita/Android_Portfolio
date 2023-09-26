package com.vikvita.fooddelivery.data.local

import android.content.Context
import androidx.room.Room

object LocalDB {

    fun getDao(context: Context):DatabaseDAO{
        synchronized(context){
           return Room.databaseBuilder(context
               .applicationContext,
               Database::class.java,
               "dishes.db"
           ).build().databaseDAO()

        }


    }
}