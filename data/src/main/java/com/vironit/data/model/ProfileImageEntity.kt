package com.vironit.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.vironit.domain.model.ProfileImage
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ProfileImageEntity(
    @SerializedName("small") override val small: String,
    @SerializedName("medium") override val medium: String,
    @SerializedName("large") override val large: String
) : Parcelable, ProfileImage