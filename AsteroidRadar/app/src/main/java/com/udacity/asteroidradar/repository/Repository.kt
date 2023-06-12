package com.udacity.asteroidradar.repository


import android.view.animation.Transformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.APIKEY
import com.udacity.asteroidradar.api.AsteroidNetwork
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult

import com.udacity.asteroidradar.asDatabaseModel
import com.udacity.asteroidradar.databse.AsteroidDB
import com.udacity.asteroidradar.databse.AsteroidDatabase
import com.udacity.asteroidradar.databse.asDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class Repository(private val database:AsteroidDatabase) {
    val asteroids:LiveData<List<Asteroid>> = Transformations.map(database.asteroidDatabaseDao.getAllAsteroids()){
            it.asDomainModel()

            }
     suspend fun refreshList(){
          withContext(Dispatchers.IO){

              val asteroids =AsteroidNetwork.retrofitService.getAsteroids(APIKEY)

              val jsonObject:JSONObject=JSONObject(asteroids)
              parseAsteroidsJsonResult(jsonObject).asDatabaseModel().forEach{
                  database.asteroidDatabaseDao.insert(it)
              }

                  }
              }
          }





