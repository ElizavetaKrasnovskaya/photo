package com.vironit.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.vironit.data.mapper.PhotoMapperImpl
import com.vironit.data.model.PhotoEntity
import com.vironit.data.service.RetrofitInstance
import com.vironit.domain.model.Photo
import com.vironit.domain.repository.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val mapper: PhotoMapperImpl) : Repository {
    override fun getAllData(page: Int, pageLimit: Int, order: String): Call<MutableList<Photo>> {
        val getPost = RetrofitInstance.api.getRecentPhotos(page, pageLimit, order)
//        getPost.enqueue(object : Callback<MutableList<Photo>> {
//            override fun onResponse(
//                call: Call<MutableList<Photo>>,
//                response: Response<MutableList<Photo>>
//            ) {
//                if (response.isSuccessful) {
//                    photos.clear()
//                    Log.d("response", response.body().toString())
//                    response.body()?.let { photos.addAll(it) }
//                } else {
//                    Log.d("response", response.body().toString())
//                }
//            }
//
//            override fun onFailure(call: Call<MutableList<Photo>>, t: Throwable) {
//                Log.d("Response", "Failed")
//            }
//
//        })
        return getPost
    }
}