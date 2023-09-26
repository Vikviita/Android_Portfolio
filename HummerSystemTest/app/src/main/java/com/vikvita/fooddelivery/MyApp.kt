package com.vikvita.fooddelivery

import android.app.Application
import com.vikvita.fooddelivery.data.local.DatabaseDAO
import com.vikvita.fooddelivery.data.local.LocalDB
import com.vikvita.fooddelivery.data.local.LocalRepository
import com.vikvita.fooddelivery.data.remote.APIService
import com.vikvita.fooddelivery.data.remote.ApiNetwork
import com.vikvita.fooddelivery.domain.DataSource
import com.vikvita.fooddelivery.screen.MenuFragment
import com.vikvita.fooddelivery.screen.MenurFragmentViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApp:Application() {

    override fun onCreate() {
        super.onCreate()
        val myModule=module {
            viewModel {
                MenurFragmentViewModel(get(),
                    get() as DataSource)
            }
            single { ApiNetwork.createService()}
            single { LocalRepository(get(),get()) as DataSource }
            single { LocalDB.getDao(this@MyApp) as DatabaseDAO }

        }


        startKoin{
            androidContext(this@MyApp)
           modules(listOf(myModule))
        }
    }


}