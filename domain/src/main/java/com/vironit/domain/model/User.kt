package com.vironit.domain.model

data class User(
    val id: String,
    val username: String,
    val name: String,
    val profileImage: ProfileImage,
    val totalPhotos: String,
    val collection: String
)