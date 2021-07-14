package com.vironit.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@kotlinx.parcelize.Parcelize
data class ProfileImage(
    val small: String?,
    val medium: String?,
    val large: String?
): Parcelable