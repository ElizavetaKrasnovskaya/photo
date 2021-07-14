package com.vironit.data.retrofit.model

import com.google.gson.annotations.SerializedName
import com.vironit.data.database.model.ProfileImageEntity
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashProfileImage(
    @SerializedName("small") val small: String?,
    @SerializedName("medium") val medium: String?,
    @SerializedName("large") val large: String?
) {
    fun convertToProfileImageEntity(userId: String): ProfileImageEntity {
        return ProfileImageEntity(
            this.small,
            this.medium,
            this.large,
            userId
        )
    }
}