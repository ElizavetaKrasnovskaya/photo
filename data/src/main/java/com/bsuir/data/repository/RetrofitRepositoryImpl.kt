package com.bsuir.data.repository

import com.bsuir.data.retrofit.api.ApiService
import com.bsuir.domain.repository.RetrofitRepository
import com.bsuir.domain.retrofit.model.SearchPhotoResponse
import com.bsuir.domain.retrofit.model.UnsplashPhoto
import com.bsuir.domain.retrofit.model.UnsplashUser
import retrofit2.Response
import javax.inject.Inject

class RetrofitRepositoryImpl @Inject constructor(val apiService: ApiService) : RetrofitRepository {
    override suspend fun getPhotos(
        accessKey: String,
        url: String,
        clientId: String
    ): Response<ArrayList<UnsplashPhoto>> {
        return apiService.getPhotos(accessKey, url, clientId)
    }

    override suspend fun getPhoto(
        accessKey: String,
        url: String,
        clientId: String
    ): Response<UnsplashPhoto> {
        return apiService.getPhoto(accessKey, url, clientId)
    }

    override suspend fun searchPhoto(
        accessKey: String,
        url: String,
        clientId: String,
        query: String
    ): Response<SearchPhotoResponse> {
        return apiService.searchPhoto(accessKey, url, clientId, query)
    }

    override suspend fun getUserByLinkSelf(
        accessKey: String,
        url: String,
        clientId: String
    ): Response<UnsplashUser> {
        return apiService.getUserByLinkSelf(accessKey, url, clientId)
    }
}