package com.vironit.krasnovskaya_l23_p3.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vironit.data.database.UnsplashDb
import com.vironit.data.database.model.PhotoAndUser
import com.vironit.data.retrofit.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavouritesViewModel : ViewModel() {

    val unsplashPhotos = MutableLiveData<List<UnsplashPhoto>>()

    fun getPhotos(context: Context) {
        val tempPhotos = ArrayList<UnsplashPhoto>()
        CoroutineScope(Dispatchers.IO).launch {
            val dao = UnsplashDb.getInstance(context).photoDao
            val photos = dao.getAll()
            photos.forEach {
                val photo = convertToPhoto(it)
                tempPhotos.add(photo)
            }
            unsplashPhotos.postValue(tempPhotos)
        }
    }

    fun deleteFavorite(context: Context, photoId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val dao = UnsplashDb.getInstance(context).photoDao
            dao.delete(photoId)
            getPhotos(context)
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

        val photo = photoAndUser.photoEntity!!.convertToPhoto()
        photo.unsplashUser = user
        photo.urlUnsplash = url
        photo.unsplashExif = exif

        return photo
    }
}