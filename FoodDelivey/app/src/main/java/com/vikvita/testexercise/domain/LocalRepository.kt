package com.vikvita.testexercise.domain

import androidx.lifecycle.LiveData
import com.vikvita.testexercise.data.dto.BasketItem
import com.vikvita.testexercise.data.dto.DishesItem
import com.vikvita.testexercise.data.dto.Result

interface LocalRepository {

   fun getDishes():Result<List<DishesItem>>



  fun getBasketList():Result<List<BasketItem>>

    fun addToBasketList(newItem:BasketItem)
  fun deleteFromBasketLIst(id:Int)
   fun clearBasket()

 suspend fun refreshRep()
 suspend fun refreshBusketList()

 suspend fun clearDatabaseBasket()
 suspend fun insertToDatabase(list:List<BasketItem>)

}