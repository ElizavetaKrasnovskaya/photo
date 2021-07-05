package com.vironit.domain.use_cases

import com.vironit.domain.model.Photo
import com.vironit.domain.repository.Repository
import retrofit2.Call
import javax.inject.Inject

class UseCase @Inject constructor(private val repository: Repository) {
    fun getAllData(
        page: Int,
        pageLimit: Int,
        order: String
    ): Call<MutableList<Photo>> {
        return repository.getAllData(page, pageLimit, order)
    }
}