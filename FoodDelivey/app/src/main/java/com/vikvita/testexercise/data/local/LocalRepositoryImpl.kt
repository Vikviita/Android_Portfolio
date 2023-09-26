package com.vikvita.testexercise.data.local





import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.vikvita.testexercise.R
import com.vikvita.testexercise.domain.LocalRepository
import com.vikvita.testexercise.data.dto.BasketItem
import com.vikvita.testexercise.data.dto.DishesItem
import com.vikvita.testexercise.data.dto.Result
import com.vikvita.testexercise.data.remote.RemoteNetwork
import com.vikvita.testexercise.data.remote.RemoteService
import com.vikvita.testexercise.data.remote.parseDishesJsonResult
import com.vikvita.testexercise.utils.doubleIt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import org.json.JSONObject
import javax.xml.transform.TransformerConfigurationException

class LocalRepositoryImpl(val database: DatabaseDAO,val remote: RemoteService): LocalRepository {


    var dishList= listOf<DishesItem>()
    var basketList= mutableListOf<BasketItem>()

    override fun getDishes(): Result<List<DishesItem>> {
      return Result.Succes(dishList)
    }



    override fun getBasketList(): Result<List<BasketItem>> {
        return Result.Succes(basketList.toList())
    }


    override fun addToBasketList(newItem: BasketItem) {
           val item = basketList.find { it.id == newItem.id }

           if (item == null) {
               newItem.incrementAmount()
               basketList.add(newItem)
           } else {
               item.incrementAmount()
           }


    }

    override fun deleteFromBasketLIst(id: Int) {

            val item = basketList.find { it.id == id }

            item!!.decrementAmount()
            if (item.amount == 0) {
                basketList.remove(item)
            }

        }

    override fun clearBasket() {
       basketList.clear()

    }

    override suspend fun refreshRep() {
        withContext(Dispatchers.IO) {


            val dishes=remote.getDishLIst()
            val jsonObject= JSONObject(dishes)

            val array:Array<DishesItem> = parseDishesJsonResult(jsonObject)



            database.insertAll(dishesItem = array)

            dishList=database.getList()

        }
    }

    override suspend fun refreshBusketList() {
        withContext(Dispatchers.IO) {
            val databaseList = database.getBasketList()
            if (databaseList.isEmpty()) {
                return@withContext
            }
            else{
                basketList=databaseList.toMutableList()
            }
        }
    }

    override suspend fun clearDatabaseBasket() {
        withContext(Dispatchers.IO){
            database.clearBasket()
        }
    }

    override suspend fun insertToDatabase(list: List<BasketItem>) {
        withContext(Dispatchers.IO) {
            list.forEach {
                database.insertInBasket(it)
            }
        }
    }


}





