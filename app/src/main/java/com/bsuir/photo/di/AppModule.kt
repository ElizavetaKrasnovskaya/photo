package com.bsuir.photo.di

import android.app.Application
import android.content.Context
import com.bsuir.data.database.UnsplashDb
import com.bsuir.data.repository.DatabaseRepositoryImpl
import com.bsuir.data.repository.RetrofitRepositoryImpl
import com.bsuir.data.repository.SearchRepositoryImpl
import com.bsuir.data.retrofit.api.ApiService
import com.bsuir.data.retrofit.api.ClientHttp
import com.bsuir.domain.interactor.DatabaseInteractor
import com.bsuir.domain.interactor.RetrofitInteractor
import com.bsuir.domain.interactor.SearchInteractor
import com.bsuir.domain.repository.DatabaseRepository
import com.bsuir.domain.repository.RetrofitRepository
import com.bsuir.domain.repository.SearchRepository
import com.bsuir.domain.use_cases.DatabaseUseCase
import com.bsuir.domain.use_cases.RetrofitUseCase
import com.bsuir.domain.use_cases.SearchUseCase
import com.bsuir.photo.common.util.BASE_URL
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(val application: Application) {

    @Singleton
    @Provides
    fun getSearchInteractor(): SearchInteractor {
        return SearchInteractor(getSearchUseCase())
    }

    @Singleton
    @Provides
    fun getRetrofitInteractor(): RetrofitInteractor {
        return RetrofitInteractor(getRetrofitUseCase())
    }

    @Singleton
    @Provides
    fun getDatabaseInteractor(): DatabaseInteractor {
        return DatabaseInteractor(getDatabaseUseCase())
    }

    @Singleton
    @Provides
    fun getSearchUseCase(): SearchUseCase {
        return SearchUseCase(getSearchRepository())
    }

    @Singleton
    @Provides
    fun getRetrofitUseCase(): RetrofitUseCase {
        return RetrofitUseCase(getRetrofitRepository())
    }

    @Singleton
    @Provides
    fun getDatabaseUseCase(): DatabaseUseCase {
        return DatabaseUseCase(getDatabaseRepository())
    }


    @Singleton
    @Provides
    fun getSearchRepository(): SearchRepository {
        return SearchRepositoryImpl(getRoomDbInstance())
    }

    @Singleton
    @Provides
    fun getRetrofitRepository(): RetrofitRepository {
        return RetrofitRepositoryImpl(getApiService())
    }

    @Singleton
    @Provides
    fun getDatabaseRepository(): DatabaseRepository {
        return DatabaseRepositoryImpl(getRoomDbInstance())
    }

    @Singleton
    @Provides
    fun getSearchDb(): UnsplashDb {
        return UnsplashDb.getInstance(provideAppContext())
    }

    @Singleton
    @Provides
    fun getApiService(): ApiService {
        return ClientHttp.getRetrofit(BASE_URL).create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun getRoomDbInstance(): UnsplashDb {
        return UnsplashDb.getInstance(provideAppContext())
    }

    @Singleton
    @Provides
    fun provideAppContext(): Context {
        return application.applicationContext
    }
}