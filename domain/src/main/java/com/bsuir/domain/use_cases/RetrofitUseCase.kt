package com.bsuir.domain.use_cases

import com.bsuir.domain.repository.RetrofitRepository
import com.bsuir.domain.retrofit.model.SearchPhotoResponse
import com.bsuir.domain.retrofit.model.UnsplashPhoto
import com.bsuir.domain.retrofit.model.UnsplashUser
import retrofit2.Response
import javax.inject.Inject

class RetrofitUseCase @Inject constructor(val repository: RetrofitRepository) {
    suspend fun getPhotos(
        accessKey: String,
        url: String,
        clientId: String
    ): Response<ArrayList<UnsplashPhoto>> {
        return repository.getPhotos(accessKey, url, clientId)
    }

    suspend fun getPhoto(
        accessKey: String,
        url: String,
        clientId: String
    ): Response<UnsplashPhoto> {
        return repository.getPhoto(accessKey, url, clientId)
    }

    suspend fun searchPhoto(
        accessKey: String,
        url: String,
        clientId: String,
        query: String
    ): Response<SearchPhotoResponse> {
        return repository.searchPhoto(accessKey, url, clientId, query)
    }

    suspend fun getUserByLinkSelf(
        accessKey: String,
        url: String,
        clientId: String
    ): Response<UnsplashUser> {
        return repository.getUserByLinkSelf(accessKey, url, clientId)
    }
}