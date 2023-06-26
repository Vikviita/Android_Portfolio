package com.vikvita.wheatherapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.vikvita.wheatherapp.data.dto.WheatherCommonInfo
import com.vikvita.wheatherapp.data.dto.WheatherItem

@Dao
interface DatabaseDAO {
    @Insert
    fun insertList(vararg wheatherItem: WheatherItem)
    @Query("SELECT * FROM item_table")
    fun getWheateList():LiveData<List<WheatherItem>>
    @Insert
    fun insertCommonInfo(wheatherCommonInfo: WheatherCommonInfo)
    @Query("SELECT * FROM info_table")
    fun getInfo():LiveData<WheatherCommonInfo>


    @Query("DELETE FROM item_table")
    fun clearList()
    @Query("DELETE FROM info_table")
    fun clearInfo()
}