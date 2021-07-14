package com.vironit.krasnovskaya_l23_p3.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vironit.data.database.UnsplashDb
import com.vironit.data.database.model.*
import com.vironit.data.retrofit.api.ApiService
import com.vironit.data.retrofit.api.ClientHttp
import com.vironit.data.retrofit.api.RequestError
import com.vironit.data.retrofit.model.*
import com.vironit.krasnovskaya_l23_p3.ACCESS_KEY
import com.vironit.krasnovskaya_l23_p3.BASE_URL
import com.vironit.krasnovskaya_l23_p3.CLIENT_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhotoDetailsViewModel : ViewModel() {

    val unsplashPhoto = MutableLiveData<UnsplashPhoto>()
    val showProgress = MutableLiveData<Boolean>()
    val requestError = MutableLiveData<String>()

    fun getPhoto(context: Context, photoId: String, isOnline: Boolean) {
        if (isOnline) {
            showProgress.postValue(true)
            try {
                CoroutineScope(Dispatchers.IO).launch {
                    val call =
                        ClientHttp.getRetrofit(BASE_URL).create(ApiService::class.java)
                            .getPhoto(
                                ACCESS_KEY,
                                "photos/$photoId/",
                                CLIENT_ID
                            )

                    if (call.isSuccessful) {
                        val photo = call.body()
                        unsplashPhoto.postValue(photo!!)
                    } else {
                        requestError.postValue(RequestError.getByValue(call.code()).toString())
                    }
                }
                showProgress.postValue(false)
            } catch (e: Throwable) {
                requestError.postValue(RequestError.getByValue(0).toString())
                showProgress.postValue(false)
            }
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                showProgress.postValue(true)
                val dao = UnsplashDb.getInstance(context).photoDao
                val photoEntity = dao.getById(photoId)

                var profileImage: UnsplashProfileImage? = null
                var user: UnsplashUser? = null
                var urls: UnsplashPhotoUrl? = null
                var exif: UnsplashExif? = null

                photoEntity.user?.let { userEntity ->
                    userEntity.profileImageEntity?.let { profileImageEntity ->
                        profileImage = profileImageEntity.convertToProfileImage()
                    }
                    user = profileImage?.let { userEntity.userEntity.convertToUser(it) }
                }

                photoEntity.photoUrlEntity.let { photoUrlEntity ->
                    if (photoUrlEntity != null) {
                        urls = photoUrlEntity.convertToPhotoUrl()
                    }
                }

                photoEntity.exifEntity.let { exifEntity ->
                    if (exifEntity != null) {
                        exif = exifEntity.convertToExif()
                    }
                }

                val photo = photoEntity.photoEntity.convertToPhoto()
                photo.unsplashUser = user
                photo.urlUnsplash = urls
                photo.unsplashExif = exif

                showProgress.postValue(false)
                unsplashPhoto.postValue(photo)
            }
        }
    }

    fun savePhoto(context: Context, photo: UnsplashPhoto) {
        var exifEntity: ExifEntity? = null
        var photoEntity: PhotoEntity? = null
        var photoUrlEntity: PhotoUrlEntity? = null
        var profileImageEntity: ProfileImageEntity? = null
        var userEntity: UserEntity? = null

        photo.let { photoEntity = it.convertToPhotoEntity() }
        photo.urlUnsplash.let { urls ->
            if (urls != null) {
                photoUrlEntity = photo.id?.let { urls.convertToPhotoUrlEntity(it) }
            }
        }
        photo.unsplashExif.let { exif ->
            if (exif != null) {
                exifEntity = photo.id?.let { exif.convertToExifEntity(it) }
            }
        }
        photo.unsplashUser.let { user ->
            if (user != null) {
                userEntity = photo.id?.let { user.convertToUserEntity(it) }
                user.unsplashProfileImage.let { profileImage ->
                    if (profileImage != null) {
                        profileImageEntity =
                            user.id?.let { profileImage.convertToProfileImageEntity(it) }
                    }
                }
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            photoEntity.let { photo ->
                val photoDao = UnsplashDb.getInstance(context).photoDao
                photo?.let { photoDao.insert(it) }

                exifEntity.let { exif ->
                    val exifDao = UnsplashDb.getInstance(context).exifDao
                    exif?.let { exifDao.insert(it) }
                }
                photoUrlEntity.let { photoUrl ->
                    val photoUrlDao = UnsplashDb.getInstance(context).photoUrlDao
                    photoUrl?.let { photoUrlDao.insert(it) }
                }
                userEntity.let { user ->
                    val userDao = UnsplashDb.getInstance(context).userDao
                    user?.let { userDao.insert(it) }

                    profileImageEntity.let { profileImage ->
                        val profileImageDao = UnsplashDb.getInstance(context).profileImageDao
                        profileImage?.let { profileImageDao.insert(it) }
                    }
                }
            }
        }
    }
}