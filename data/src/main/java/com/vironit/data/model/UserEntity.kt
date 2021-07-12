package com.vironit.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class UserEntity(
    @SerializedName("id") val id: String?,
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("second_name") val secondName: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("instagram_username") val inst: String?,
    @SerializedName("twitter_username") val twitter: String?,
    @SerializedName("portfolio_url") val portfolio: String?,
    @SerializedName("profile_image") val profileImage: ProfileImageEntity?,
    @SerializedName("total_photos") val totalPhotos: String?,
    @SerializedName("total_collections") val collection: String?
)