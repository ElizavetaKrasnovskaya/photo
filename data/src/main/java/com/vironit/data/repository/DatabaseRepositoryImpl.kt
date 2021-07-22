package com.vironit.data.repository

import com.vironit.data.database.UnsplashDb
import com.vironit.domain.database.model.*
import com.vironit.domain.repository.DatabaseRepository
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(val database: UnsplashDb) : DatabaseRepository {

    override suspend fun insertUser(user: UserEntity) {
        database.userDao.insert(user)
    }

    override suspend fun insertExif(exif: ExifEntity) {
        database.exifDao.insert(exif)
    }

    override suspend fun insertPhotoUrl(photoUrl: PhotoUrlEntity) {
        database.photoUrlDao.insert(photoUrl)
    }

    override suspend fun insertProfileImage(profileImage: ProfileImageEntity) {
        database.profileImageDao.insert(profileImage)
    }

    override suspend fun getAllPhotos(): List<PhotoAndUser> {
        return database.photoDao.getAll()
    }

    override suspend fun getPhotoById(photoId: String): PhotoAndUser {
        return database.photoDao.getById(photoId)
    }

    override suspend fun searchPhoto(query: String): List<PhotoAndUser> {
        return database.photoDao.searchPhoto(query)
    }

    override suspend fun insertPhoto(photo: PhotoEntity) {
        database.photoDao.insert(photo)
    }

    override suspend fun deletePhoto(photoId: String) {
        return database.photoDao.delete(photoId)
    }

    suspend fun getAllUsers(): List<UserEntity>{
        return database.userDao.getAll()
    }

    suspend fun getAllExif(): List<ExifEntity>{
        return database.exifDao.getAll()
    }

    suspend fun getAllPhotoUrl(): List<PhotoUrlEntity>{
        return database.photoUrlDao.getAll()
    }

    suspend fun getAllProfileImage(): List<ProfileImageEntity>{
        return database.profileImageDao.getAll()
    }
}