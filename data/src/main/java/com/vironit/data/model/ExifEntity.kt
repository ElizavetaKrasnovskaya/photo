package com.vironit.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.vironit.domain.model.Exif
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ExifEntity(
    @SerializedName("make") override val make: String,
    @SerializedName("model") override val model: String,
    @SerializedName("exposure_time") override val exposure: String,
    @SerializedName("aperture") override val aperture: String,
    @SerializedName("focal_length") override val focal: String,
    @SerializedName("iso") override val iso: String
) : Parcelable, Exif