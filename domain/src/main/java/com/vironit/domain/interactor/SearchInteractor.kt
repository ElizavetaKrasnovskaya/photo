package com.vironit.domain.interactor

import com.vironit.domain.database.model.SearchEntity
import com.vironit.domain.use_cases.SearchUseCase
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