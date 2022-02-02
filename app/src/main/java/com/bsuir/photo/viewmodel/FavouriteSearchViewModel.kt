package com.bsuir.photo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bsuir.domain.database.model.SearchEntity
import com.bsuir.domain.interactor.SearchInteractor
import com.bsuir.photo.MyApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteSearchViewModel(application: Application) :
    AndroidViewModel(application) {

    @Inject
    lateinit var searchInteractor: SearchInteractor

    val searchList = MutableLiveData<List<SearchEntity>>()

    init {
        (application as MyApp).getAppComponent().injectFavouriteSearchViewModel(this)
    }

    fun getSearches() {
        CoroutineScope(Dispatchers.IO).launch {
            searchList.postValue(searchInteractor.getFavourite())
        }
    }

    fun deleteSearch(searchId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            searchInteractor.delete(searchId)
            getSearches()
        }
    }
}