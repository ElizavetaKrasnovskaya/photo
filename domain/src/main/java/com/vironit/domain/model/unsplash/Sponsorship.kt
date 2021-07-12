package com.vironit.domain.model.unsplash

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sponsorship(
    val tagline: String?
) : Parcelable