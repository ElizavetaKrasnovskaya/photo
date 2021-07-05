package com.vironit.domain.model

data class Photo(
    val id: String,
    val width: String,
    val height: String,
    val color: String,
    val description: String,
    val url: PhotoUrl,
    val user: User,
    val likes: String,
    val sponsorship: Sponsorship,
    val exif: Exif
)