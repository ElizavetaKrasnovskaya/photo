package com.vironit.data.repository

//import androidx.paging.Pager
//import androidx.paging.PagingConfig
//import com.vironit.data.retrofit.UnsplashPagingSource
//import com.vironit.data.mapper.PhotoMapper
//import com.vironit.domain.model.Photo
//import com.vironit.domain.repository.Repository
//import javax.inject.Inject
//import javax.inject.Singleton
//
//@Singleton
//class UnsplashRepository @Inject constructor(
//    private val unsplashApi: UnsplashApi,
//    val photoMapper: PhotoMapper
//) :
//    Repository {
//
//    override fun getSearchResults(query: String) =
//        Pager(
//            config = PagingConfig(
//                pageSize = 20,
//                maxSize = 100,
//                enablePlaceholders = false
//            ),
//            pagingSourceFactory = { UnsplashPagingSource(photoMapper, unsplashApi, query) }
//        ).liveData
//
//    override suspend fun getById(id: String): Photo {
//        return photoMapper.fromEntity(unsplashApi.getById(id))
//    }
//}