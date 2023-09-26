package com.vikvita.testexercise.screens.BasketScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vikvita.testexercise.domain.LocalRepository
import com.vikvita.testexercise.data.dto.BasketItem
import com.vikvita.testexercise.data.dto.Result
import kotlinx.coroutines.launch

class BasketFragmentViewModel(val datasource: LocalRepository):ViewModel() {

   private val _listOfBasketItem =MutableLiveData<List<BasketItem>>()
    val listOfBasketItem:LiveData<List<BasketItem>> = _listOfBasketItem

    private val _price = MutableLiveData<Int>()
    val price:LiveData<Int> =_price






    init{
        viewModelScope.launch {
            datasource.refreshBusketList()
            updateList()
        }


    }

    fun updateList(){
           _listOfBasketItem.value = (datasource.getBasketList() as Result.Succes).data
            countPrice()

    }
    fun addItem(newItem: BasketItem){

            datasource.addToBasketList(newItem)

            updateList()

    }
    fun deleteItem(id:Int){

            datasource.deleteFromBasketLIst(id)

            updateList()

    }

    fun countPrice(){
        var sum=0
        listOfBasketItem.value?.forEach {
           sum+= it.amount * it.price
        }
        _price.value=sum
    }

    fun clear(){

            datasource.clearBasket()
        viewModelScope.launch {
            datasource.clearDatabaseBasket()
        }
            updateList()

    }

    fun saveBusket() {
        viewModelScope.launch {
            datasource.insertToDatabase((datasource.getBasketList() as Result.Succes).data)
        }
    }



}