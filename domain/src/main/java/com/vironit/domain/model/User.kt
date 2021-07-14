package com.vironit.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.RawValue
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    val id: String?,
    val firstName: String?,
    val secondName: String?,
    val name: String?,
    val inst: String?,
    val twitter: String?,
    val portfolio: String?,
    val profileImage: @RawValue ProfileImage?,
    val totalPhotos: String?,
    val collection: String?
): Parcelable