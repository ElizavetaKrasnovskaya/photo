package com.vironit.krasnovskaya_l23_p3.di

import android.app.Application
import android.content.Context
import com.vironit.data.database.UnsplashDb
import com.vironit.data.repository.DatabaseRepositoryImpl
import com.vironit.data.repository.RetrofitRepositoryImpl
import com.vironit.data.repository.SearchRepositoryImpl
import com.vironit.data.retrofit.api.ApiService
import com.vironit.data.retrofit.api.ClientHttp
import com.vironit.domain.interactor.DatabaseInteractor
import com.vironit.domain.interactor.RetrofitInteractor
import com.vironit.domain.interactor.SearchInteractor
import com.vironit.domain.repository.DatabaseRepository
import com.vironit.domain.repository.RetrofitRepository
import com.vironit.domain.repository.SearchRepository
import com.vironit.domain.use_cases.DatabaseUseCase
import com.vironit.domain.use_cases.RetrofitUseCase
import com.vironit.domain.use_cases.SearchUseCase
import com.vironit.krasnovskaya_l23_p3.BASE_URL
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