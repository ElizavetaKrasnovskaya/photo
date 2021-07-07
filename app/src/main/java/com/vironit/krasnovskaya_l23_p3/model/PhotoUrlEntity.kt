package com.vironit.krasnovskaya_l23_p3.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class PhotoUrlEntity(
    @SerializedName("full") val full: String,
    @SerializedName("regular") val regular: String
): Parcelable