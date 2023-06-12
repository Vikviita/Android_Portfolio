package com.vikvita.shoestore.screens.list


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vikvita.shoestore.models.Shoe

class ListViewModel: ViewModel() {
    private val arrayList=ArrayList<Shoe>()
    private var _shoeList=MutableLiveData<List<Shoe>>()
    var shoeList:LiveData<List<Shoe>> =_shoeList

    private val _check=MutableLiveData<Boolean>()
    val check:LiveData<Boolean> = _check


    fun addNewShoe(name:String,company:String,size:String,description:String){
        val shoe=Shoe(name,company,size,description)
        arrayList.add(shoe)
        _shoeList.value= arrayList
        _check.value=true
    }
}