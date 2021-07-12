package com.vironit.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.vironit.domain.model.unsplash.Photo


interface Repository {
    fun getSearchResults(query: String): LiveData<PagingData<Photo>>
}