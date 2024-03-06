package com.example.formulamoney.application

import android.app.Application
import com.example.data.di.DataModuleProvider
import com.example.formulamoney.di.AppModuleProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            AppModuleProvider.loadModules()
            DataModuleProvider.loadModules()
        }
    }

}