package com.vikvita.testexercise.data.local

import android.content.Context
import androidx.room.Room

object LocalDB {


    private lateinit var INSTACE:DatabaseDAO
    fun createDatabaseDao(context: Context): DatabaseDAO {
        synchronized(this) {
            if (::INSTACE.isInitialized) {
                return INSTACE
            } else {

                return Room.databaseBuilder(
                    context.applicationContext,
                    Database::class.java, "dishes.db")
                    .build().dishesDAO()
            }
        }
    }
    }

