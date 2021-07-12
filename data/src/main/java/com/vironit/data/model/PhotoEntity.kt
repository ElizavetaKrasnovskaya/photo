package com.vironit.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.vironit.domain.model.*
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class PhotoEntity(
    @SerializedName("id") override val id: String,
    @SerializedName("width") override val width: String,
    @SerializedName("height") override val height: String,
    @SerializedName("color") override val color: String,
    @SerializedName("alt_description") override val description: String,
    @SerializedName("urls") override val url: @RawValue PhotoUrlEntity,
    @SerializedName("user") override val user: @RawValue UserEntity,
    @SerializedName("likes") override val likes: String,
    @SerializedName("sponsorship") override val sponsorship: @RawValue SponsorshipEntity,
    @SerializedName("exif") override val exif: @RawValue ExifEntity,
    @SerializedName("created_at") override val date: String,
) : Parcelable, Photo