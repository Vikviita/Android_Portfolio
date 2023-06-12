package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidNetwork

import com.udacity.asteroidradar.databse.getDatabase
import com.udacity.asteroidradar.repository.Repository
import kotlinx.coroutines.launch


import java.lang.IllegalArgumentException
import java.net.UnknownHostException

class MainViewModel(app:Application) : AndroidViewModel(app) {
    private val _showDetail=MutableLiveData<Asteroid>()
    val showDetail:LiveData<Asteroid> =_showDetail

    fun asteroidClick(asteroid:Asteroid){
        _showDetail.value=asteroid
    }
    fun asteroidClicked(){
        _showDetail.value=null
    }



    private val _pictureOfDay=MutableLiveData<PictureOfDay>()
    val pictureOfDay:LiveData<PictureOfDay> =_pictureOfDay

    private val _showToast=MutableLiveData<Boolean>()
    val showToast:LiveData<Boolean> =_showToast

    private val database = getDatabase(app)
    private val repository = Repository(database)

    var asteroidList=repository.asteroids
    fun start() {
        viewModelScope.launch {
            try {

                val pictureOfDay = AsteroidNetwork.retrofitService.getPictureOfDay()
                _pictureOfDay.value = pictureOfDay
                repository.refreshList()


            } catch (e: UnknownHostException) {
                _showToast.value=true

            }
        }
    }

    fun toastShowed(){
        _showToast.value=false
    }
init {
        start()
    }


}


class Factory(val app:Application):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel= when(modelClass){
            MainViewModel::class.java->MainViewModel(app) as T
            else->{
                throw IllegalArgumentException("unable to construct view model")

            }
        }
        return viewModel as T
    }

}





