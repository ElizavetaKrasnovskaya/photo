package com.vironit.krasnovskaya_l23_p3.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.vironit.domain.database.model.SearchEntity
import com.vironit.domain.interactor.SearchInteractor
import com.vironit.krasnovskaya_l23_p3.MyApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.KProperty

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