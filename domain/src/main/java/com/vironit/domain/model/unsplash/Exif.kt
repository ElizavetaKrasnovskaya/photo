package com.vironit.domain.model.unsplash

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Exif(
    val make: String?,
    val model: String?,
    val exposure: String?,
    val aperture: String?,
    val focal: String?,
    val iso: String?,
) : Parcelable