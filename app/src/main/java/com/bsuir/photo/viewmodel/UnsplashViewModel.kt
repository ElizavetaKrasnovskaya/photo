package com.bsuir.photo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bsuir.data.retrofit.api.RequestError
import com.bsuir.domain.database.model.SearchEntity
import com.bsuir.domain.interactor.RetrofitInteractor
import com.bsuir.domain.interactor.SearchInteractor
import com.bsuir.domain.retrofit.model.UnsplashPhoto
import com.bsuir.photo.MyApp
import com.bsuir.photo.common.util.ACCESS_KEY
import com.bsuir.photo.common.util.CLIENT_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class UnsplashViewModel(
    application: Application
) : AndroidViewModel(application) {

    @Inject
    lateinit var retrofitInteractor: RetrofitInteractor

    @Inject
    lateinit var searchInteractor: SearchInteractor

    private var page: Int = 1
    val unsplashPhotos = MutableLiveData<List<UnsplashPhoto>>()
    val showProgress = MutableLiveData<Boolean>()
    val requestError = MutableLiveData<String>()

    init {
        (application as MyApp).getAppComponent().injectUnsplashViewModel(this)
    }

    fun getPhotos() {
        showProgress.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call =
                    retrofitInteractor
                        .getPhotos(
                            ACCESS_KEY,
                            "photos/?page=$page",
                            CLIENT_ID
                        )
                if (call.isSuccessful) {
                    val photos = call.body()
                    page += 1
                    unsplashPhotos.postValue(ArrayList())
                    unsplashPhotos.postValue(photos!!)
                } else {
                    requestError.postValue(RequestError.getByValue(call.code()).toString())
                }
                showProgress.postValue(false)
            } catch (e: Throwable) {
                requestError.postValue(RequestError.getByValue(0).toString())
                showProgress.postValue(false)
            }
        }
    }

    fun searchPhotos(query: String) {
        showProgress.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call =
                    retrofitInteractor
                        .searchPhoto(
                            ACCESS_KEY,
                            "search/photos/?page=$page",
                            CLIENT_ID,
                            query
                        )
                if (call.isSuccessful) {
                    val searchPhoto = call.body()
                    val check = call.headers()["x-total"]
                    print(check)

                    page += 1
                    unsplashPhotos.postValue(ArrayList())
                    unsplashPhotos.postValue(searchPhoto!!.results)
                } else {
                    requestError.postValue(RequestError.getByValue(call.code()).toString())
                }
                showProgress.postValue(false)
            } catch (e: Throwable) {
                requestError.postValue(RequestError.getByValue(0).toString())
                showProgress.postValue(false)
            }
        }
    }

    fun saveSearch(query: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call =
                    retrofitInteractor
                        .searchPhoto(
                            ACCESS_KEY,
                            "search/photos/?page=$page",
                            CLIENT_ID,
                            query
                        )
                if (call.isSuccessful) {
                    val total = call.headers()["x-total"]
                    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                    val currentDate = sdf.format(Date())
                    saveSearchToDb(SearchEntity(query, total, currentDate, false))
                }
            } catch (e: Throwable) {
                requestError.postValue(RequestError.getByValue(0).toString())
                showProgress.postValue(false)
            }
        }
    }

    private fun saveSearchToDb(searchEntity: SearchEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            searchInteractor.insert(searchEntity)
        }
    }
}