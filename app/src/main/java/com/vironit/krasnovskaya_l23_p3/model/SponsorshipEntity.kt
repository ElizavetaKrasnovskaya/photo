package com.vironit.krasnovskaya_l23_p3.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class SponsorshipEntity(@SerializedName("tagline") val tagline: String) : Parcelable