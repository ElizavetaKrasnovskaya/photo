package com.vironit.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileImageEntity(
    @SerializedName("small") val small: String?,
    @SerializedName("medium") val medium: String?,
    @SerializedName("large") val large: String?
)