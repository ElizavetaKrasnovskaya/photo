package com.vironit.krasnovskaya_l23_p3.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.vironit.domain.database.model.PhotoAndUser
import com.vironit.domain.interactor.DatabaseInteractor
import com.vironit.domain.retrofit.model.*
import com.vironit.krasnovskaya_l23_p3.MyApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouritesViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var databaseInteractor: DatabaseInteractor
    val unsplashPhotos = MutableLiveData<List<UnsplashPhoto>>()

    init {
        (application as MyApp).getAppComponent().injectFavouritesViewModel(this)
    }

    fun getPhotos() {
        val tempPhotos = ArrayList<UnsplashPhoto>()
        CoroutineScope(Dispatchers.IO).launch {
            val photos = databaseInteractor.getAllPhotos()
            photos.forEach {
                val photo = convertToPhoto(it)
                tempPhotos.add(photo)
            }
            unsplashPhotos.postValue(tempPhotos)
        }
    }

    fun deleteFavorite(photoId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            databaseInteractor.deletePhoto(photoId)
            getPhotos()
        }
    }

    private fun convertToPhoto(photoAndUser: PhotoAndUser): UnsplashPhoto {
        var profileImages: UnsplashProfileImage? = null
        var user: UnsplashUser? = null
        var url: UnsplashPhotoUrl? = null
        var exif: UnsplashExif? = null

        photoAndUser.user?.let { userEntity ->
            userEntity.profileImageEntity?.let { profileImageEntity ->
                profileImages = profileImageEntity.convertToProfileImage()
            }

            user = profileImages?.let { userEntity.userEntity.convertToUser(it) }
        }

        photoAndUser.photoUrlEntity?.let { photoUrlEntity ->
            url = photoUrlEntity.convertToPhotoUrl()
        }

        photoAndUser.exifEntity?.let { exifEntity ->
            exif = exifEntity.convertToExif()
        }

        val photo = photoAndUser.photoEntity.convertToPhoto()
        photo.unsplashUser = user
        photo.urlUnsplash = url
        photo.unsplashExif = exif

        return photo
    }
}