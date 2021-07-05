package com.vironit.krasnovskaya_l23_p3

import android.app.Application
import com.vironit.krasnovskaya_l23_p3.di.AppComponent
import com.vironit.krasnovskaya_l23_p3.di.AppModule
import com.vironit.krasnovskaya_l23_p3.di.DaggerAppComponent

class App : Application() {
    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule())
            .build()
    }

    fun getAppComponent(): AppComponent {
        return appComponent
    }
}