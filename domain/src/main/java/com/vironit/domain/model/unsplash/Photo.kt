package com.vironit.domain.model.unsplash

import android.os.Parcelable
import kotlinx.android.parcel.RawValue
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(
    val id: String?,
    val width: String?,
    val height: String?,
    val color: String?,
    val description: String?,
    val url: @RawValue PhotoUrl?,
    val user: @RawValue User?,
    val likes: String?,
    val sponsorship: @RawValue Sponsorship?,
    val exif: @RawValue Exif?,
    val date: String?
) : Parcelable