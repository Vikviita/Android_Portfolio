package com.vikvita.wheatherapp.screens.main

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.vikvita.wheatherapp.domain.DataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragmentViewModel( val data:DataSource):ViewModel() {
    val list= data.getList()
    val info= data.getInfo()



    fun start(latLng: LatLng){
        viewModelScope.launch{
            try{

                data.refreshRep(latLng)
        }
        catch (e:Exception){

        }

        }


    }

}