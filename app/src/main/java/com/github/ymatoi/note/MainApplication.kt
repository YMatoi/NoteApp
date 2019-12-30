package com.github.ymatoi.note

import android.app.Application
import com.github.ymatoi.note.di.databaseModule
import com.github.ymatoi.note.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(
                viewModelModule,
                databaseModule
            ))
        }
    }
}
