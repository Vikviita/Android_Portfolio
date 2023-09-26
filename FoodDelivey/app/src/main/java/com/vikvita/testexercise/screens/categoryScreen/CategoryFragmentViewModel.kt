package com.vikvita.testexercise.screens.categoryScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vikvita.testexercise.domain.LocalRepository
import com.vikvita.testexercise.data.dto.BasketItem
import com.vikvita.testexercise.data.dto.DishesItem
import com.vikvita.testexercise.data.dto.Result
import com.vikvita.testexercise.utils.Event
import com.vikvita.testexercise.utils.TagsOfDish
import kotlinx.coroutines.launch

class CategoryFragmentViewModel(val dataSource: LocalRepository):ViewModel() {
   private val _listOfDished = MutableLiveData<List<DishesItem>>()
    val listOfDished:LiveData<List<DishesItem>> = _listOfDished
    private val _showToast=MutableLiveData<Event<String>>()
    val showToast:LiveData<Event<String>> = _showToast

    private lateinit var initialList:List<DishesItem>
init {
    viewModelScope.launch {
        dataSource.refreshRep()
        updateList((dataSource.getDishes() as Result.Succes).data)
    }
}

    fun filterChanged(listOfTag:TagsOfDish){
       val list=(dataSource.getDishes() as Result.Succes).data
        updateList(
            list.filter {
                it.tegs.contains(listOfTag)
            }
        )

    }

    fun updateList(list:List<DishesItem>){
        _listOfDished.value= list
    }


    fun showToast(msg: String){
        _showToast.value= Event(msg)
    }

   fun saveToBasket(item:BasketItem){

           dataSource.addToBasketList(item)

   }
}