package com.vironit.krasnovskaya_l23_p3.api

import com.vironit.krasnovskaya_l23_p3.model.PhotoEntity


data class UnsplashResponse(
    val results: List<PhotoEntity>
)