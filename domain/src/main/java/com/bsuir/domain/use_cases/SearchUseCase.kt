package com.bsuir.domain.use_cases

import com.bsuir.domain.database.model.SearchEntity
import com.bsuir.domain.repository.SearchRepository
import javax.inject.Inject

class SearchUseCase @Inject constructor(val repository: SearchRepository) {
    suspend fun getAll(): List<SearchEntity> {
        return repository.getAll()
    }

    suspend fun insert(search: SearchEntity) {
        repository.insert(search)
    }

    suspend fun delete(searchId: Int) {
        repository.delete(searchId)
    }

    suspend fun getFavourite(): List<SearchEntity> {
        return repository.getFavourite()
    }

    suspend fun update(id: Int, isFavourite: Int) {
        repository.update(id, isFavourite)
    }
}