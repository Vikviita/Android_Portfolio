package com.vikvita.wheatherapp.data.local

import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import com.vikvita.wheatherapp.data.dto.WheatherCommonInfo
import com.vikvita.wheatherapp.data.dto.WheatherItem
import com.vikvita.wheatherapp.data.remote.WheatherService
import com.vikvita.wheatherapp.data.remote.parseJsonWheatherInfo
import com.vikvita.wheatherapp.data.remote.parseJsonWheatherItem
import com.vikvita.wheatherapp.domain.DataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class LocalRepository(val local:DatabaseDAO,val remote:WheatherService): DataSource {
   private val list:LiveData<List<WheatherItem>> = local.getWheateList()
    private val wheatherCommonInfo:LiveData<WheatherCommonInfo> = local.getInfo()

    override suspend fun refreshRep(latlng: LatLng) {

        val geoLocation="${latlng.latitude},${latlng.longitude}"
        val response= remote.getInfo(location = geoLocation)
        val jsonObject= JSONObject(response)
        val result= parseJsonWheatherItem(jsonObject)
        val resultInfo = parseJsonWheatherInfo(jsonObject)

        withContext(Dispatchers.IO) {
            local.clearInfo()
            local.insertCommonInfo(wheatherCommonInfo=resultInfo)
            local.clearList()
    local.insertList(wheatherItem = result)


} }




    override fun getList(): LiveData<List<WheatherItem>> {
       return list
    }

    override fun getInfo(): LiveData<WheatherCommonInfo> {
       return wheatherCommonInfo
    }



}