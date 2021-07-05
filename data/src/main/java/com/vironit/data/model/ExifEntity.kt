package com.vironit.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ExifEntity(
    @SerializedName("make")  val make: String,
    @SerializedName("model")  val model: String,
    @SerializedName("exposure_time")  val exposure: String,
    @SerializedName("aperture")  val aperture: String,
    @SerializedName("focal_length")  val focal: String,
    @SerializedName("iso")  val iso: String)