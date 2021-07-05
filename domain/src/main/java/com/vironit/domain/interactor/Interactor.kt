package com.vironit.domain.interactor

import com.vironit.domain.model.Photo
import com.vironit.domain.use_cases.UseCase
import retrofit2.Call
import javax.inject.Inject

class Interactor @Inject constructor(private val useCase: UseCase) {
    fun getAllData(
        page: Int,
        pageLimit: Int,
        order: String
    ): Call<MutableList<Photo>> {
        return useCase.getAllData(page, pageLimit, order)
    }
}