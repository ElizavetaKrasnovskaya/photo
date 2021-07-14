package com.vironit.data.retrofit.model

import com.google.gson.annotations.SerializedName
import com.vironit.data.database.model.ExifEntity
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashExif(
    @SerializedName("make") val make: String?,
    @SerializedName("model") val model: String?,
    @SerializedName("exposure_time") val exposure: String?,
    @SerializedName("aperture") val aperture: String?,
    @SerializedName("focal_length") val focal: String?,
    @SerializedName("iso") val iso: String?
) {
    fun convertToExifEntity(photoId: String): ExifEntity {
        return ExifEntity(
            this.make,
            this.model,
            this.exposure,
            this.aperture,
            this.focal,
            this.iso,
            photoId
        )
    }
}