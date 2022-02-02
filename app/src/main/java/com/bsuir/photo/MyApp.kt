package com.bsuir.photo

import android.app.Application
import com.bsuir.photo.di.AppComponent
import com.bsuir.photo.di.AppModule
import com.bsuir.photo.di.DaggerAppComponent

class MyApp : Application() {
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    fun getAppComponent(): AppComponent {
        return appComponent
    }
}