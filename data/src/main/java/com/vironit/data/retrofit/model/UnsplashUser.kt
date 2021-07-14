package com.vironit.data.retrofit.model

import com.google.gson.annotations.SerializedName
import com.vironit.data.database.model.UserEntity
import kotlinx.serialization.Serializable

@Serializable
data class UnsplashUser(
    @SerializedName("id") val id: String,
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("second_name") val secondName: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("instagram_username") val inst: String?,
    @SerializedName("twitter_username") val twitter: String?,
    @SerializedName("portfolio_url") val portfolio: String?,
    @SerializedName("profile_image") val unsplashProfileImage: UnsplashProfileImage?,
    @SerializedName("total_photos") val totalPhotos: String?,
    @SerializedName("total_collections") val collection: String?
) {
    fun convertToUserEntity(photoId: String): UserEntity {
        return UserEntity(
            this.id,
            this.firstName,
            this.secondName,
            this.name,
            this.inst,
            this.twitter,
            this.portfolio,
            this.totalPhotos,
            this.collection,
            photoId
        )
    }
}