package com.vironit.domain.use_cases

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.vironit.domain.repository.Repository
import javax.inject.Inject

class UseCase<T : Any> @Inject constructor(private val repository: Repository<T>) {
    fun getSearchResults(query: String): LiveData<PagingData<T>> {
        return repository.getSearchResults(query)
    }
}