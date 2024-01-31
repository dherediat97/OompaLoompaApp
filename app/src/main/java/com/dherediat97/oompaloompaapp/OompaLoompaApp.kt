package com.dherediat97.oompaloompaapp

import android.app.Application
import com.dherediat97.oompaloompaapp.di.networkModule
import com.dherediat97.oompaloompaapp.di.repositoryModule
import com.dherediat97.oompaloompaapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class OompaLoompaApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@OompaLoompaApp)
            modules(networkModule, repositoryModule, viewModelModule)
        }
    }
}