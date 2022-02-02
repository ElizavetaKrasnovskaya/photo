package com.bsuir.domain.retrofit.model

import com.bsuir.domain.database.model.PhotoEntity
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashPhoto(
    @SerializedName("id") val id: String,
    @SerializedName("width") val width: String?,
    @SerializedName("height") val height: String?,
    @SerializedName("color") val color: String?,
    @SerializedName("alt_description") val description: String?,
    @SerializedName("urls") var urlUnsplash: UnsplashPhotoUrl?,
    @SerializedName("user") var unsplashUser: UnsplashUser?,
    @SerializedName("likes") val likes: String?,
    @SerializedName("exif") var unsplashExif: UnsplashExif?,
    @SerializedName("created_at") val date: String?
) {
    fun convertToPhotoEntity(): PhotoEntity {
        return PhotoEntity(
            this.id,
            this.color,
            this.height,
            this.color,
            this.description,
            this.likes,
            this.date
        )
    }
}