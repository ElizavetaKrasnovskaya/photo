package com.bsuir.photo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.bsuir.data.retrofit.api.RequestError
import com.bsuir.domain.database.model.*
import com.bsuir.domain.interactor.DatabaseInteractor
import com.bsuir.domain.interactor.RetrofitInteractor
import com.bsuir.domain.retrofit.model.*
import com.bsuir.photo.MyApp
import com.bsuir.photo.common.util.ACCESS_KEY
import com.bsuir.photo.common.util.CLIENT_ID
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PhotoDetailsViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var retrofitInteractor: RetrofitInteractor

    @Inject
    lateinit var databaseInteractor: DatabaseInteractor

    val unsplashPhoto = MutableLiveData<UnsplashPhoto>()
    val showProgress = MutableLiveData<Boolean>()
    val requestError = MutableLiveData<String>()

    init {
        (application as MyApp).getAppComponent().injectPhotoDetailsViewModel(this)
    }

    fun getPhoto(photoId: String, isOnline: Boolean) {
        if (isOnline) {
            showProgress.postValue(true)
            try {
                CoroutineScope(Dispatchers.IO).launch {
                    val call =
                        retrofitInteractor
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
                val photoEntity = databaseInteractor.getPhotoById(photoId)

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

    fun savePhoto(photo: UnsplashPhoto) {
        var exifEntity: ExifEntity? = null
        var photoEntity: PhotoEntity?
        var photoUrlEntity: PhotoUrlEntity? = null
        var profileImageEntity: ProfileImageEntity? = null
        var userEntity: UserEntity? = null

        photo.let { photoEntity = it.convertToPhotoEntity() }
        photo.urlUnsplash.let { urls ->
            if (urls != null) {
                photoUrlEntity = photo.id.let { urls.convertToPhotoUrlEntity(it) }
            }
        }
        photo.unsplashExif.let { exif ->
            if (exif != null) {
                exifEntity = photo.id.let { exif.convertToExifEntity(it) }
            }
        }
        photo.unsplashUser.let { user ->
            if (user != null) {
                userEntity = photo.id.let { user.convertToUserEntity(it) }
                user.unsplashProfileImage.let { profileImage ->
                    if (profileImage != null) {
                        profileImageEntity =
                            user.id.let { profileImage.convertToProfileImageEntity(it) }
                    }
                }
            }
        }
        CoroutineScope(Dispatchers.IO).launch {
            photoEntity.let { photo ->
                photo?.let { databaseInteractor.insertPhoto(it) }

                exifEntity.let { exif ->
                    exif?.let { databaseInteractor.insertExif(it) }
                }
                photoUrlEntity.let { photoUrl ->
                    photoUrl?.let { databaseInteractor.insertPhotoUrl(it) }
                }
                userEntity.let { user ->
                    user?.let { databaseInteractor.insertUser(it) }

                    profileImageEntity.let { profileImage ->
                        profileImage?.let { databaseInteractor.insertProfileImage(it) }
                    }
                }
            }
        }
    }
}