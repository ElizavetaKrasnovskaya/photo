package com.vironit.domain.repository

import com.vironit.domain.database.model.*


interface DatabaseRepository {
    suspend fun insertUser(user: UserEntity)
    suspend fun insertExif(exif: ExifEntity)
    suspend fun insertPhotoUrl(photoUrl: PhotoUrlEntity)
    suspend fun insertProfileImage(profileImage: ProfileImageEntity)
    suspend fun getAllPhotos(): List<PhotoAndUser>
    suspend fun getPhotoById(photoId: String): PhotoAndUser
    suspend fun searchPhoto(query: String): List<PhotoAndUser>
    suspend fun insertPhoto(photo: PhotoEntity)
    suspend fun deletePhoto(photoId: String)
}