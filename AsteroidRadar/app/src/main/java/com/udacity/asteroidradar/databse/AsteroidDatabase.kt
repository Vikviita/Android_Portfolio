package com.udacity.asteroidradar.databse

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AsteroidDB::class], version = 1)
abstract class AsteroidDatabase :RoomDatabase(){
abstract val asteroidDatabaseDao:AsteroidDatabaseDao}


private lateinit var INSTANCE : AsteroidDatabase

fun getDatabase(context: Context):AsteroidDatabase{
    synchronized(AsteroidDatabase::class.java){
        if(!::INSTANCE.isInitialized){
            INSTANCE= Room.databaseBuilder(context.applicationContext,AsteroidDatabase::class.java,"asteroids").build()
        }
        return INSTANCE
    }
}
