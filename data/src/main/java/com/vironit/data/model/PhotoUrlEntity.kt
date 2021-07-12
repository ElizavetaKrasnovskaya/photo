package com.vironit.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.vironit.domain.model.PhotoUrl
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class PhotoUrlEntity(
    @SerializedName("full") override val full: String,
    @SerializedName("regular") override val regular: String
) : Parcelable, PhotoUrl