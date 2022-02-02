package com.bsuir.domain.use_cases

import com.bsuir.domain.database.model.*
import com.bsuir.domain.repository.DatabaseRepository
import javax.inject.Inject


class DatabaseUseCase @Inject constructor(private val repository: DatabaseRepository) {
    suspend fun insertUser(user: UserEntity) {
        repository.insertUser(user)
    }

    suspend fun insertExif(exif: ExifEntity) {
        repository.insertExif(exif)
    }

    suspend fun insertPhotoUrl(photoUrl: PhotoUrlEntity) {
        repository.insertPhotoUrl(photoUrl)
    }

    suspend fun insertProfileImage(profileImage: ProfileImageEntity) {
        repository.insertProfileImage(profileImage)
    }

    suspend fun getAllPhotos(): List<PhotoAndUser> {
        return repository.getAllPhotos()
    }

    suspend fun getPhotoById(photoId: String): PhotoAndUser {
        return repository.getPhotoById(photoId)
    }

    suspend fun searchPhoto(query: String): List<PhotoAndUser> {
        return repository.searchPhoto(query)
    }

    suspend fun insertPhoto(photo: PhotoEntity) {
        repository.insertPhoto(photo)
    }

    suspend fun deletePhoto(photoId: String) {
        repository.deletePhoto(photoId)
    }
}