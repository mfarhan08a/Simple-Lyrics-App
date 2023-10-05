package com.mfarhan08a.simplelyricsapp

import android.app.Application
import com.mfarhan08a.simplelyricsapp.core.di.databaseModule
import com.mfarhan08a.simplelyricsapp.core.di.networkModule
import com.mfarhan08a.simplelyricsapp.core.di.repositoryModule
import com.mfarhan08a.simplelyricsapp.di.useCaseModule
import com.mfarhan08a.simplelyricsapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}