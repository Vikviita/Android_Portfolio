package com.vikvita.fooddelivery.data.local

import com.vikvita.fooddelivery.data.dto.DishItem
import com.vikvita.fooddelivery.data.remote.APIService
import com.vikvita.fooddelivery.data.remote.parseJSON
import com.vikvita.fooddelivery.domain.DataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class LocalRepository(val remote:APIService,val database:DatabaseDAO):DataSource {


    override suspend fun getList(): Array<DishItem> {
        val result = remote.getList("a")
        val jsonObject = JSONObject(result)
        return parseJSON(jsonObject)
    }

    override suspend fun getListFromDB(): Array<DishItem> {
        var array:Array<DishItem>
        withContext(Dispatchers.IO){
            array= database.getList()
    }
        return array
}
    override suspend fun insertToDatabase(array: Array<DishItem>) {
        withContext(Dispatchers.IO){
            database.insert(item = array)
        }
    }

    override suspend fun clearDB() {
        withContext(Dispatchers.IO){
            database.clear()
        }
    }

}