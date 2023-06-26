package com.vikvita.wheatherapp.domain

import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import com.vikvita.wheatherapp.data.dto.WheatherCommonInfo
import com.vikvita.wheatherapp.data.dto.WheatherItem

interface DataSource {
    suspend fun refreshRep(latlng:LatLng)





    fun getList():LiveData<List<WheatherItem>>
    fun getInfo():LiveData<WheatherCommonInfo>
}