package com.bsuir.domain.retrofit.model

import com.bsuir.domain.database.model.ProfileImageEntity
import com.google.gson.annotations.SerializedName
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