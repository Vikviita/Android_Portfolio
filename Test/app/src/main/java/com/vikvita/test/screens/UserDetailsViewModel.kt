package com.vikvita.test.screens

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vikvita.test.UserNotFoundException
import com.vikvita.test.models.User
import com.vikvita.test.models.UserDetail
import com.vikvita.test.models.UserService

class UserDetailsViewModel(
    private val userService: UserService
) :ViewModel(){
    private val _usersDetails=MutableLiveData<UserDetail>()
    val usersDetails:LiveData<UserDetail> = _usersDetails

    fun loadUser(userId:Int){
try {
   _usersDetails.value= userService.getById(userId)
}
catch (e:UserNotFoundException){
    e.printStackTrace()
}
    }


    fun deleteUser(){
        val userDetails=this.usersDetails.value ?: return
    userService.deleteUsers(userDetails.user)
    }

}