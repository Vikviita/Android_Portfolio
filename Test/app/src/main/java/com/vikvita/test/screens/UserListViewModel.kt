package com.vikvita.test.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vikvita.test.models.User
import com.vikvita.test.models.UserListener
import com.vikvita.test.models.UserService

class UserListViewModel(private val userService: UserService):ViewModel() {

    private val _users =MutableLiveData<List<User>>()
    val users:LiveData<List<User>> = _users

    private val listener:UserListener={
        _users.value=it
    }

    fun loadUser(){
        userService.addListener(listener)
    }
init {
    loadUser()
}

    override fun onCleared() {
        super.onCleared()
        userService.removeListener(listener)
    }


    fun moveUser(user: User, moveBy:Int){
        userService.moveUser(user,moveBy)
    }

    fun deleteUser(user:User){
        userService.deleteUsers(user)
    }


}