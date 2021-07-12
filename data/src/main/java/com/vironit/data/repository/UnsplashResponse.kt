package com.vironit.data.repository

import com.vironit.data.model.PhotoEntity

data class UnsplashResponse(
    val results: List<PhotoEntity>
)