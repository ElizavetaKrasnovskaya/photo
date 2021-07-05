package com.vironit.data.service

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL: String = "https://api.unsplash.com"
const val API_KEY: String = "qCQQ0lT9lKhZtNNGXWdwBHOwrVXw7fuMV_QmGwxISmU"

object RetrofitInstance {

    private val retrofit by lazy {
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(Interceptor {chain: Interceptor.Chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization","Client-ID " + API_KEY)
                    .build()
                chain.proceed(request)
            })
            .build()
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
    val api : ApiInterface by lazy {
        retrofit.create(ApiInterface::class.java)
    }
}