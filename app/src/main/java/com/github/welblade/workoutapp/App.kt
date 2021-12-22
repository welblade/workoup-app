package com.github.welblade.workoutapp

import android.app.Application
import com.github.welblade.workoutapp.data.di.DataModules
import com.github.welblade.workoutapp.domain.di.DomainModules
import com.github.welblade.workoutapp.presentation.di.PresentationModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
        }
        DataModules.load()
        DomainModules.load()
        PresentationModules.load()
    }
}