package com.example.mypictionary.di

import android.app.Application
import timber.log.Timber

class PictionaryApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}