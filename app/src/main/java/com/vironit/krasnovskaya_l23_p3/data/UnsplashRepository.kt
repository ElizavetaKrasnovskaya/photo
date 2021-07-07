package com.vironit.krasnovskaya_l23_p3.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.vironit.krasnovskaya_l23_p3.api.UnsplashApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UnsplashRepository @Inject constructor(private val unsplashApi: UnsplashApi) {

    fun getSearchResults(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { UnsplashPagingSource(unsplashApi, query) }
        ).liveData
}