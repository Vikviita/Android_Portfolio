package com.vikvita.testexercise.data.dto

sealed class Result<out T:Any> {


    class Succes<out T:Any>(val data:T):Result<T>()

    class Error(val message:String?) :Result<Nothing>()
}