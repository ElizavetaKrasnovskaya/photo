package com.vironit.krasnovskaya_l23_p3.di

import com.vironit.data.mapper.*
import com.vironit.data.repository.RepositoryImpl
import com.vironit.domain.interactor.Interactor
import com.vironit.domain.repository.Repository
import com.vironit.domain.use_cases.UseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule() {

    @Singleton
    @Provides
    fun getInteractor(): Interactor {
        return Interactor(getUseCase())
    }

    @Singleton
    @Provides
    fun getUseCase(): UseCase {
        return UseCase(getRepository())
    }

    @Singleton
    @Provides
    fun getRepository(): Repository {
        return RepositoryImpl(getPhotoMapperImpl())
    }

    @Singleton
    @Provides
    fun getPhotoMapperImpl(): PhotoMapperImpl {
        return PhotoMapperImpl(
            getPhotoUrlMapperImpl(), getUserMapperImpl(),
            getSponsorshipMapperImpl(), getExifMapperImpl()
        )
    }

    @Singleton
    @Provides
    fun getPhotoUrlMapperImpl(): PhotoUrlMapperImpl {
        return PhotoUrlMapperImpl()
    }

    @Singleton
    @Provides
    fun getUserMapperImpl(): UserMapperImpl {
        return UserMapperImpl(getProfileImageMapperImpl())
    }

    @Singleton
    @Provides
    fun getProfileImageMapperImpl(): ProfileImageMapperImpl {
        return ProfileImageMapperImpl()
    }

    @Singleton
    @Provides
    fun getSponsorshipMapperImpl(): SponsorshipMapperImpl {
        return SponsorshipMapperImpl()
    }

    @Singleton
    @Provides
    fun getExifMapperImpl(): ExifMapperImpl {
        return ExifMapperImpl()
    }
}