package com.vironit.domain.model

import android.os.Parcelable

interface PhotoUrl : Parcelable {
    val full: String
    val regular: String
}