package com.vironit.krasnovskaya_l23_p3.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ExifEntity(
    @SerializedName("make")  val make: String,
    @SerializedName("model")  val model: String,
    @SerializedName("exposure_time")  val exposure: String,
    @SerializedName("aperture")  val aperture: String,
    @SerializedName("focal_length")  val focal: String,
    @SerializedName("iso")  val iso: String): Parcelable