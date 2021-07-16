package com.vironit.domain.repository

import com.vironit.domain.retrofit.model.SearchPhotoResponse
import com.vironit.domain.retrofit.model.UnsplashPhoto
import com.vironit.domain.retrofit.model.UnsplashUser
import retrofit2.Response


interface RetrofitRepository {
    suspend fun getPhotos(
        accessKey: String,
        url: String,
        clientId: String
    ): Response<ArrayList<UnsplashPhoto>>

    suspend fun getPhoto(accessKey: String, url: String, clientId: String): Response<UnsplashPhoto>

    suspend fun searchPhoto(
        accessKey: String,
        url: String,
        clientId: String,
        query: String
    ): Response<SearchPhotoResponse>

    suspend fun getUserByLinkSelf(
        accessKey: String,
        url: String,
        clientId: String
    ): Response<UnsplashUser>
}