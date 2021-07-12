package com.vironit.domain.model

import android.os.Parcelable

interface Exif: Parcelable {
    val make: String
    val model: String
    val exposure: String
    val aperture: String
    val focal: String
    val iso: String
}