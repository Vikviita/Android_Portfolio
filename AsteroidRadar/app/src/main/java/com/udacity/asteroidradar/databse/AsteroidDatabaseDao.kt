package com.udacity.asteroidradar.databse

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid



@Dao
interface AsteroidDatabaseDao {

    @Query("select * from database_asteroid_table")
    fun getAllAsteroids():LiveData<List<AsteroidDB>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(asteroids:AsteroidDB)

}
