package com.bsuir.domain.retrofit.model

import com.bsuir.domain.database.model.PhotoUrlEntity
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashPhotoUrl(
    @SerializedName("full") val full: String?,
    @SerializedName("regular") val regular: String?
) {
    fun convertToPhotoUrlEntity(photoId: String): PhotoUrlEntity {
        return PhotoUrlEntity(
            this.full,
            this.regular,
            photoId
        )
    }
}