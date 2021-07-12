package com.vironit.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class SponsorshipEntity(@SerializedName("tagline") val tagline: String?)