package com.vironit.krasnovskaya_l23_p3.di

import com.vironit.krasnovskaya_l23_p3.ui.PhotosViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(employeeViewModel: PhotosViewModel)
}