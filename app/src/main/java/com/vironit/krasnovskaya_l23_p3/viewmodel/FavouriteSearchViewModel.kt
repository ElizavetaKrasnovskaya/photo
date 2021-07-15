package com.vironit.krasnovskaya_l23_p3.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vironit.data.database.UnsplashDb
import com.vironit.data.database.model.SearchEntity
import com.vironit.data.retrofit.model.UnsplashPhoto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouriteSearchViewModel: ViewModel() {

    val searchList = MutableLiveData<List<SearchEntity>>()

    fun getSearches(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val dao = UnsplashDb.getInstance(context).searchDao
            searchList.postValue(dao.getFavourite())
        }
    }

    fun deleteSearch(context: Context, searchId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val dao = UnsplashDb.getInstance(context).searchDao
            dao.delete(searchId)
            getSearches(context)
        }
    }

}