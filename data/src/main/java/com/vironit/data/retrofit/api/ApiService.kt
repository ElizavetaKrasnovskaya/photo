package com.vironit.data.retrofit.api

import com.vironit.data.retrofit.model.SearchPhotoResponse
import com.vironit.data.retrofit.model.UnsplashPhoto
import com.vironit.data.retrofit.model.UnsplashUser
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