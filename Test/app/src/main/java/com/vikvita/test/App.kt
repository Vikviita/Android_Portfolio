package com.vikvita.test

import android.app.Application
import com.vikvita.test.models.UserService

class App: Application() {
val userService=UserService()
}