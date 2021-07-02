package com.vironit.domain.repository

interface Repository {

    fun getAllData()

    fun getDataById()

    suspend fun insert()

    suspend fun deleteDataById(id: Long)
}