package com.vikvita.test

import com.vikvita.test.models.User

interface Navigator {
    fun showDetail(user: User)
    fun goBack()
    fun toast(messageRes:Int)

}