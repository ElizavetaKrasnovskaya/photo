package com.vironit.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoUrlEntity(@SerializedName("full")  val full: String,
                          @SerializedName("regular")  val regular: String)