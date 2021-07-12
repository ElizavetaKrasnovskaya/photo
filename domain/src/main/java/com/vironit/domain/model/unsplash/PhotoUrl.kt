package com.vironit.domain.model.unsplash

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PhotoUrl(
    val full: String?,
    val regular: String?
) : Parcelable