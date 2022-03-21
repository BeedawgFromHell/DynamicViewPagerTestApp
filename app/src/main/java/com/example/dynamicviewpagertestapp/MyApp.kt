package com.example.dynamicviewpagertestapp

import android.app.Application
import com.example.dynamicviewpagertestapp.di.mainModule
import org.koin.core.context.startKoin

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(mainModule)
        }
    }
}