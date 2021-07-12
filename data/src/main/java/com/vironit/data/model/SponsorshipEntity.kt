package com.vironit.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.vironit.domain.model.Sponsorship
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class SponsorshipEntity(@SerializedName("tagline") override val tagline: String) : Parcelable,
    Sponsorship