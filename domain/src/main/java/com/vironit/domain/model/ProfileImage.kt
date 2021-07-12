package com.vironit.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

interface ProfileImage: Parcelable {
    val small: String
    val medium: String
    val large: String
}