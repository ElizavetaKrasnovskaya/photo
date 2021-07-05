package com.vironit.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoEntity(@SerializedName("id")val id : String,
                       @SerializedName("width") val width : String,
                       @SerializedName("height")  val height: String,
                       @SerializedName("color")  val color: String,
                       @SerializedName("alt_description")  val description: String,
                       @SerializedName("urls")  val urlEntity: PhotoUrlEntity,
                       @SerializedName("user")  val userEntity: UserEntity,
                       @SerializedName("likes")  val likes: String,
                       @SerializedName("sponsorship") val sponsorshipEntity: SponsorshipEntity,
                       @SerializedName("exif")  val exifEntity: ExifEntity)