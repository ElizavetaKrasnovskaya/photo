package com.bsuir.domain.interactor

import com.bsuir.domain.database.model.*
import com.bsuir.domain.use_cases.DatabaseUseCase
import javax.inject.Inject

class DatabaseInteractor @Inject constructor(val useCase: DatabaseUseCase) {
    suspend fun insertUser(user: UserEntity) {
        useCase.insertUser(user)
    }

    suspend fun insertExif(exif: ExifEntity) {
        useCase.insertExif(exif)
    }

    suspend fun insertPhotoUrl(photoUrl: PhotoUrlEntity) {
        useCase.insertPhotoUrl(photoUrl)
    }

    suspend fun insertProfileImage(profileImage: ProfileImageEntity) {
        useCase.insertProfileImage(profileImage)
    }

    suspend fun getAllPhotos(): List<PhotoAndUser> {
        return useCase.getAllPhotos()
    }

    suspend fun getPhotoById(photoId: String): PhotoAndUser {
        return useCase.getPhotoById(photoId)
    }

    suspend fun searchPhoto(query: String): List<PhotoAndUser> {
        return useCase.searchPhoto(query)
    }

    suspend fun insertPhoto(photo: PhotoEntity) {
        useCase.insertPhoto(photo)
    }

    suspend fun deletePhoto(photoId: String) {
        useCase.deletePhoto(photoId)
    }
}