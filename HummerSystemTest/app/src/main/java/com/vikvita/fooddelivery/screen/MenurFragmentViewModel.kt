package com.vikvita.fooddelivery.screen

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vikvita.fooddelivery.data.dto.DishItem
import com.vikvita.fooddelivery.domain.DataSource
import com.vikvita.fooddelivery.util.Event
import kotlinx.coroutines.launch
import java.lang.Exception
import java.net.InetAddress


class MenurFragmentViewModel(app:Application,val dataSource:DataSource) :ViewModel(){
    private val _showToasts = MutableLiveData<Event<String>>()
    val showToasts:LiveData<Event<String>> = _showToasts

    private val _list=MutableLiveData<Array<DishItem>>()
    val list:LiveData<Array<DishItem>> =_list

init {
    downloadList()
}


    fun showToast(msg:String){
        _showToasts.value= Event(msg)
    }



    fun downloadList(){
        viewModelScope.launch {
                try {
                    _list.value = dataSource.getList()
                    dataSource.clearDB()
                    dataSource.insertToDatabase(_list.value!!)
                }
                catch (e:Exception){
                       _list.value=dataSource.getListFromDB()
                }
            }



    }



}