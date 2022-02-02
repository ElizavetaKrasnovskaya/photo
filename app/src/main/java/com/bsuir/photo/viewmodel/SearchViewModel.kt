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

class SearchViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    @Inject
    lateinit var searchInteractor: SearchInteractor
    val searchList = MutableLiveData<List<SearchEntity>>()

    init {
        (application as MyApp).getAppComponent().injectSearchViewModel(this)
    }

    fun getSearches() {
        CoroutineScope(Dispatchers.IO).launch {
            searchList.postValue(searchInteractor.getAll())
        }
    }

    fun addToFavourites(searchId: Int, isFavourite: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            searchInteractor.update(searchId, isFavourite)
            getSearches()
        }
    }
}