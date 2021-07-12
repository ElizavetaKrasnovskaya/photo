package com.vironit.domain.interactor

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.vironit.domain.model.Photo
import com.vironit.domain.use_cases.UseCase
import javax.inject.Inject

class Interactor<T: Any> @Inject constructor(private val useCase: UseCase<T>) {
    fun getSearchResults(query: String): LiveData<PagingData<T>> {
        return useCase.getSearchResults(query)
    }
}