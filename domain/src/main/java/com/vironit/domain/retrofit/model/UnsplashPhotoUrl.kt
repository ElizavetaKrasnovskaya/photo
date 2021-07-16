package com.vironit.domain.retrofit.model

import com.google.gson.annotations.SerializedName
import com.vironit.domain.database.model.PhotoUrlEntity
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