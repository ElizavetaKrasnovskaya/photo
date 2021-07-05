package com.vironit.data.model

import com.google.gson.annotations.SerializedName

data class UserEntity(@SerializedName("id")  val id: String,
                      @SerializedName("username")  val username: String,
                      @SerializedName("name") val name : String,
                      @SerializedName("profile_image")  val profileImageEntity : ProfileImageEntity,
                      @SerializedName("total_photos")  val totalPhotos: String,
                      @SerializedName("total_collections")  val collection: String)