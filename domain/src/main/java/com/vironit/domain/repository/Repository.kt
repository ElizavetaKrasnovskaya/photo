package com.vironit.domain.repository

import com.vironit.domain.model.Photo
import retrofit2.Call

interface Repository {
    fun getAllData(
        page: Int,
        pageLimit: Int,
        order: String
    ): Call<MutableList<Photo>>
}