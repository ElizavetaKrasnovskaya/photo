package com.bsuir.data.repository

import com.bsuir.data.database.UnsplashDb
import com.bsuir.domain.database.model.SearchEntity
import com.bsuir.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(val database: UnsplashDb) : SearchRepository {
    override suspend fun getAll(): List<SearchEntity> {
        return database.searchDao.getAll()
    }

    override suspend fun insert(search: SearchEntity) {
        database.searchDao.insert(search)
    }

    override suspend fun delete(searchId: Int) {
        return database.searchDao.delete(searchId)
    }

    override suspend fun getFavourite(): List<SearchEntity> {
        return database.searchDao.getFavourite()
    }

    override suspend fun update(id: Int, isFavourite: Int) {
        return database.searchDao.update(id, isFavourite)
    }
}