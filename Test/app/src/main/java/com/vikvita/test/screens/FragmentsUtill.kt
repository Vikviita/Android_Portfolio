package com.vikvita.test.screens

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vikvita.test.App
import com.vikvita.test.Navigator

class ViewModelFactory(
    private val app: App
):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
val viewModel= when (modelClass){
UserListViewModel::class.java->{
    UserListViewModel(app.userService)
}
    UserDetailsViewModel::class.java->{
        UserDetailsViewModel(app.userService)
    }
else->{
    throw IllegalStateException("Uknnown view model class")
}
}

        return viewModel as T
    }
}

fun Fragment.factory()=ViewModelFactory(requireContext().applicationContext as App)

fun Fragment.navigator()=requireActivity() as Navigator