package com.vironit.krasnovskaya_l23_p3.di

import com.vironit.krasnovskaya_l23_p3.viewmodel.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun injectFavouriteSearchViewModel(favouriteSearchViewModel: FavouriteSearchViewModel)
    fun injectFavouritesViewModel(favouritesViewModel: FavouritesViewModel)
    fun injectPhotoDetailsViewModel(photoDetailsViewModel: PhotoDetailsViewModel)
    fun injectSearchViewModel(searchViewModel: SearchViewModel)
    fun injectUnsplashViewModel(unsplashViewModel: UnsplashViewModel)
}