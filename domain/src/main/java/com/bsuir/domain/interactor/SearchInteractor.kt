package com.bsuir.domain.interactor

import com.bsuir.domain.database.model.SearchEntity
import com.bsuir.domain.use_cases.SearchUseCase
import javax.inject.Inject

class SearchInteractor @Inject constructor(val useCase: SearchUseCase) {
    suspend fun getAll(): List<SearchEntity> {
        return useCase.getAll()
    }

    suspend fun insert(search: SearchEntity) {
        useCase.insert(search)
    }

    suspend fun delete(searchId: Int) {
        useCase.delete(searchId)
    }

    suspend fun getFavourite(): List<SearchEntity> {
        return useCase.getFavourite()
    }

    suspend fun update(id: Int, isFavourite: Int) {
        useCase.update(id, isFavourite)
    }
}