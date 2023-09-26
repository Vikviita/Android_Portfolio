package com.vikvita.testexercise.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vikvita.testexercise.data.dto.BasketItem
import com.vikvita.testexercise.data.dto.DishesItem

@Dao
interface DatabaseDAO {
       @Insert(onConflict = OnConflictStrategy.REPLACE)
       fun insertAll(vararg dishesItem: DishesItem)

    @Delete
    fun delete(user: DishesItem)

    @Query("SELECT * FROM dishes_table")
    fun getList():List<DishesItem>


    @Query("SELECT * FROM dishes_table WHERE id=:id ")
    fun getDish(id:Int):DishesItem


    @Query("DELETE FROM dishes_table")
    fun clear()





    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertInBasket(basketItem: BasketItem)

    @Query("DELETE FROM basket_table")
    fun clearBasket()

    @Query("SELECT * FROM basket_table")
    fun getBasketList():List<BasketItem>





}