package com.vironit.krasnovskaya_l23_p3.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vironit.data.database.UnsplashDb
import com.vironit.data.database.model.*
import com.vironit.data.retrofit.api.ApiService
import com.vironit.data.retrofit.api.ClientHttp
import com.vironit.data.retrofit.api.RequestError
import com.vironit.data.retrofit.model.UnsplashPhoto
import com.vironit.krasnovskaya_l23_p3.ACCESS_KEY
import com.vironit.krasnovskaya_l23_p3.BASE_URL
import com.vironit.krasnovskaya_l23_p3.CLIENT_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UnsplashViewModel : ViewModel() {

    private var page: Int = 1
    val unsplashPhotos = MutableLiveData<List<UnsplashPhoto>>()
    val showProgress = MutableLiveData<Boolean>()
    val requestError = MutableLiveData<String>()

    fun getPhotos() {
        showProgress.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val call =
                    ClientHttp.getRetrofit(BASE_URL).create(ApiService::class.java)
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
                    ClientHttp.getRetrofit(BASE_URL).create(ApiService::class.java)
                        .searchPhoto(
                            ACCESS_KEY,
                            "search/photos/?page=$page",
                            CLIENT_ID,
                            query
                        )
                if (call.isSuccessful) {
                    val searchPhoto = call.body()
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
}