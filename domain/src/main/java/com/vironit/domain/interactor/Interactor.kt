package com.vironit.domain.interactor

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.vironit.domain.model.unsplash.Photo
import com.vironit.domain.use_cases.UseCase
import javax.inject.Inject

class Interactor @Inject constructor(private val useCase: UseCase) {
    fun getSearchResults(query: String): LiveData<PagingData<Photo>> {
        return useCase.getSearchResults(query)
    }
}