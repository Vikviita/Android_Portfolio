package com.vikvita.test.models

data class User(
    var id:Int,
    var name:String,
    var company:String,
    var photo:String
)

data class UserDetail(
    val user:User,
    val detail:String
)