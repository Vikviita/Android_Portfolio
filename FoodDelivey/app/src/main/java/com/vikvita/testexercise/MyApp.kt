package com.vikvita.testexercise

import android.app.Application
import com.vikvita.testexercise.domain.LocalRepository

import com.vikvita.testexercise.data.local.LocalDB
import com.vikvita.testexercise.data.local.LocalRepositoryImpl
import com.vikvita.testexercise.data.remote.RemoteNetwork
import com.vikvita.testexercise.screens.BasketScreen.BasketFragmentViewModel
import com.vikvita.testexercise.screens.categoryScreen.CategoryFragmentViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApp:Application(){

    override fun onCreate() {
        super.onCreate()


        val myModule = module {
            viewModel{
                m
            }
            viewModel {
                BasketFragmentViewModel(
                    get() as LocalRepository
                )
            }
            single {LocalRepositoryImpl(get(),get()) as LocalRepository }
            single{ LocalDB.createDatabaseDao(this@MyApp)}
            single { RemoteNetwork.createService() }

        }

        startKoin {
            androidContext(this@MyApp)
            modules(listOf(myModule))
        }
    }
}