package com.vironit.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoEntity(
    @SerializedName("id") val id: String?,
    @SerializedName("width") val width: String?,
    @SerializedName("height") val height: String?,
    @SerializedName("color") val color: String?,
    @SerializedName("alt_description") val description: String?,
    @SerializedName("urls") val url: PhotoUrlEntity?,
    @SerializedName("user") val user: UserEntity?,
    @SerializedName("likes") val likes: String?,
    @SerializedName("sponsorship") val sponsorship: SponsorshipEntity?,
    @SerializedName("exif") val exif: ExifEntity?,
    @SerializedName("created_at") val date: String?
)