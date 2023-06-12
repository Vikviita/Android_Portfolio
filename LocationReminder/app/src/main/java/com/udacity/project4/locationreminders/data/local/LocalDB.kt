package com.udacity.project4.locationreminders.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Singleton class that is used to create a reminder db
 */
object LocalDB {

   private lateinit var INSTACE:RemindersDao
    fun createRemindersDao(context: Context): RemindersDao {
       synchronized(this) {
           if (::INSTACE.isInitialized) {
               return INSTACE
           } else {

               return Room.databaseBuilder(
                   context.applicationContext,
                   RemindersDatabase::class.java, "locationReminders.db"
               ).build().reminderDao()
           }
       }
    }
}