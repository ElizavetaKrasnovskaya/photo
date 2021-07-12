package com.vironit.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData


interface Repository<T : Any> {
    fun getSearchResults(query: String): LiveData<PagingData<T>>
}