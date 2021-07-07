package com.vironit.krasnovskaya_l23_p3.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class PhotoEntity(
    @SerializedName("id") val id: String,
    @SerializedName("width") val width: String,
    @SerializedName("height") val height: String,
    @SerializedName("color") val color: String,
    @SerializedName("alt_description") val description: String,
    @SerializedName("urls") val urlEntity: @RawValue PhotoUrlEntity,
    @SerializedName("user") val userEntity: @RawValue UserEntity,
    @SerializedName("likes") val likes: String,
    @SerializedName("sponsorship") val sponsorshipEntity: @RawValue SponsorshipEntity,
    @SerializedName("exif") val exifEntity: @RawValue ExifEntity
) : Parcelable