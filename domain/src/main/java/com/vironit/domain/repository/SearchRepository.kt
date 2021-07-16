package com.vironit.domain.repository

import com.vironit.domain.database.model.SearchEntity

interface SearchRepository {
    suspend fun getAll(): List<SearchEntity>
    suspend fun insert(search: SearchEntity)
    suspend fun delete(searchId: Int)
    suspend fun getFavourite(): List<SearchEntity>
    suspend fun update(id: Int, isFavourite: Int)
}