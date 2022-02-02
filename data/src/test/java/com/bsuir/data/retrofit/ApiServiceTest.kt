package com.vironit.data.retrofit

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.vironit.data.repository.RetrofitRepositoryImpl
import com.vironit.data.retrofit.api.ApiService
import com.vironit.data.retrofit.api.ClientHttp
import com.vironit.domain.retrofit.model.SearchPhotoResponse
import com.vironit.domain.retrofit.model.UnsplashPhoto
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

// Field from build type: debug
val ACCESS_KEY = "qCQQ0lT9lKhZtNNGXWdwBHOwrVXw7fuMV_QmGwxISmU"

// Field from build type: debug
val BASE_URL = "https://api.unsplash.com/"

// Field from build type: debug
val CLIENT_ID = "qCQQ0lT9lKhZtNNGXWdwBHOwrVXw7fuMV_QmGwxISmU"

@RunWith(JUnit4::class)
class ApiServiceTest : TestCase() {
    private lateinit var service: ApiService

    @Before
    public override fun setUp() {
        service = ClientHttp
            .getRetrofit(BASE_URL)
            .create(ApiService::class.java)
    }

    @Test
    fun getPhoto() = runBlocking {
        val photo = service.getPhoto(ACCESS_KEY, "photos/wtZgw1nQ3FI/", CLIENT_ID).body()
        val json = MockResponseFileReader("getPhoto.json").content
        val postmanPhoto = Gson().fromJson(json, UnsplashPhoto::class.java)
        assertThat(postmanPhoto).isEqualTo(photo)
    }

    @Test
    fun searchByQuery() = runBlocking {
        val photos =
            service.searchPhoto(ACCESS_KEY, "search/photos/?per_page=1", CLIENT_ID, "fire")
                .body()
        val json = MockResponseFileReader("searchByQuery.json").content
        val postmanResult = Gson().fromJson(json, SearchPhotoResponse::class.java)
        assertThat(postmanResult).isEqualTo(photos)
    }
}