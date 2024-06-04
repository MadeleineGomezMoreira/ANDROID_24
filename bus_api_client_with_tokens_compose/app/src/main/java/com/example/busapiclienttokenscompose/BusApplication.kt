package com.example.busapiclienttokenscompose

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BusApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}