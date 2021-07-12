package com.vironit.krasnovskaya_l23_p3.di

import com.vironit.data.mapper.*
import com.vironit.data.repository.UnsplashApi
import com.vironit.data.repository.UnsplashRepository
import com.vironit.domain.interactor.Interactor
import com.vironit.domain.repository.Repository
import com.vironit.domain.use_cases.UseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(UnsplashApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideUnsplashApi(retrofit: Retrofit): UnsplashApi =
        retrofit.create(UnsplashApi::class.java)

    @Provides
    @Singleton
    fun provideExifMapper(): ExifMapper = ExifMapper()

    @Provides
    @Singleton
    fun providePhotoMapper(): PhotoMapper = PhotoMapper(
        providePhotoUrlMapper(),
        provideUserMapper(),
        provideSponsorshipMapper(),
        provideExifMapper()
    )

    @Provides
    @Singleton
    fun providePhotoUrlMapper(): PhotoUrlMapper = PhotoUrlMapper()

    @Provides
    @Singleton
    fun provideProfileImageMapper(): ProfileImageMapper = ProfileImageMapper()

    @Provides
    @Singleton
    fun provideSponsorshipMapper(): SponsorshipMapper = SponsorshipMapper()

    @Provides
    @Singleton
    fun provideUserMapper(): UserMapper = UserMapper(provideProfileImageMapper())

    @Provides
    @Singleton
    fun provideRepository(): Repository =
        UnsplashRepository(provideUnsplashApi(provideRetrofit()), providePhotoMapper())

    @Provides
    @Singleton
    fun provideUseCase(): UseCase = UseCase(provideRepository())

    @Provides
    @Singleton
    fun provideInteractor(): Interactor = Interactor(provideUseCase())
}