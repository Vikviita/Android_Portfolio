package com.vikvita.wheatherapp

import android.app.Activity
import android.app.Application
import com.vikvita.wheatherapp.data.local.DatabaseDAO
import com.vikvita.wheatherapp.data.local.LocalDB
import com.vikvita.wheatherapp.data.local.LocalRepository
import com.vikvita.wheatherapp.data.remote.WheatherNetwork
import com.vikvita.wheatherapp.data.remote.WheatherService
import com.vikvita.wheatherapp.domain.DataSource
import com.vikvita.wheatherapp.screens.main.MainFragmentViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApp:Application() {

    override fun onCreate() {
        super.onCreate()

        val myModule= module {

            viewModel{
                MainFragmentViewModel(get() as DataSource)
            }


            single { LocalRepository(get(),get()) as DataSource }
            single { LocalDB.getDatabaseDao(this@MyApp) as DatabaseDAO }
            single { WheatherNetwork.getService() as WheatherService }


        }



        startKoin{
            androidContext(this@MyApp)
            modules(listOf(myModule))

        }


    }

}