package com.vironit.domain.interactor

import com.vironit.domain.retrofit.model.SearchPhotoResponse
import com.vironit.domain.retrofit.model.UnsplashPhoto
import com.vironit.domain.retrofit.model.UnsplashUser
import com.vironit.domain.use_cases.RetrofitUseCase
import retrofit2.Response
import javax.inject.Inject

class RetrofitInteractor @Inject constructor(val useCase: RetrofitUseCase) {
    suspend fun getPhotos(
        accessKey: String,
        url: String,
        clientId: String
    ): Response<ArrayList<UnsplashPhoto>> {
        return useCase.getPhotos(accessKey, url, clientId)
    }

    suspend fun getPhoto(
        accessKey: String,
        url: String,
        clientId: String
    ): Response<UnsplashPhoto> {
        return useCase.getPhoto(accessKey, url, clientId)
    }

    suspend fun searchPhoto(
        accessKey: String,
        url: String,
        clientId: String,
        query: String
    ): Response<SearchPhotoResponse> {
        return useCase.searchPhoto(accessKey, url, clientId, query)
    }

    suspend fun getUserByLinkSelf(
        accessKey: String,
        url: String,
        clientId: String
    ): Response<UnsplashUser> {
        return useCase.getUserByLinkSelf(accessKey, url, clientId)
    }
}