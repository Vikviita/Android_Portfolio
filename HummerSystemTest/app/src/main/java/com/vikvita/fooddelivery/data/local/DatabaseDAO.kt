package com.vikvita.fooddelivery.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.vikvita.fooddelivery.data.dto.DishItem

@Dao
interface DatabaseDAO {
    @Insert
    fun insert(vararg item:DishItem)

    @Query("SELECT * FROM dish_table")
    fun getList():Array<DishItem>

    @Query("DELETE FROM dish_table ")
    fun clear()
}