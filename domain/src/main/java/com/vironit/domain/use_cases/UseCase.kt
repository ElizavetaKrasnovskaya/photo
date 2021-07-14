package com.vironit.domain.use_cases

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.vironit.domain.model.Photo
import com.vironit.domain.repository.Repository
import javax.inject.Inject
//
//class UseCase @Inject constructor(private val repository: Repository) {
//    fun getSearchResults(query: String): LiveData<PagingData<Photo>> {
//        return repository.getSearchResults(query)
//    }
//
//    suspend fun getById(id: String): Photo{
//        return repository.getById(id)
//    }
//}