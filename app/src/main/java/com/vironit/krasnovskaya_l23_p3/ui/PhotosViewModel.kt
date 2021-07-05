package com.vironit.krasnovskaya_l23_p3.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.vironit.domain.interactor.Interactor
import com.vironit.domain.model.Photo
import com.vironit.krasnovskaya_l23_p3.App
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class PhotosViewModel(application: Application) : AndroidViewModel(application) {
    @Inject
    lateinit var interactor: Interactor
    var allPhotos: ArrayList<Photo>

    init {
        (application as App).getAppComponent().inject(this)
        allPhotos = ArrayList()
        getPhotos(interactor.getAllData(1, 30, "popular"))
    }

    fun getPhotos(getPost: Call<MutableList<Photo>>) {
        getPost.enqueue(object : Callback<MutableList<Photo>> {
            override fun onResponse(
                call: Call<MutableList<Photo>>,
                response: Response<MutableList<Photo>>
            ) {
                if (response.isSuccessful) {
                    allPhotos.clear()
                    Log.d("response", response.body().toString())
                    response.body()?.let { allPhotos.addAll(it) }
                } else {
                    Log.d("response", response.body().toString())
                }
            }

            override fun onFailure(call: Call<MutableList<Photo>>, t: Throwable) {
                Log.d("Response", "Failed")
            }
        })
    }
}