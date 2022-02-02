package com.bsuir.data.retrofit.api

import com.bsuir.domain.retrofit.model.SearchPhotoResponse
import com.bsuir.domain.retrofit.model.UnsplashPhoto
import com.bsuir.domain.retrofit.model.UnsplashUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {

    @GET
    suspend fun getPhotos(
        @Header("Authorization") accessKey: String,
        @Url url: String,
        @Query("client_id") clientId: String
    ): Response<ArrayList<UnsplashPhoto>>

    @GET
    suspend fun getPhoto(
        @Header("Authorization") accessKey: String,
        @Url url: String,
        @Query("client_id") clientId: String
    ): Response<UnsplashPhoto>

    @GET
    suspend fun searchPhoto(
        @Header("Authorization") accessKey: String,
        @Url url: String,
        @Query("client_id") clientId: String,
        @Query("query") query: String
    ): Response<SearchPhotoResponse>

    @GET
    suspend fun getUserByLinkSelf(
        @Header("Authorization") accessKey: String,
        @Url url: String,
        @Query("client_id") clientId: String
    ): Response<UnsplashUser>
}