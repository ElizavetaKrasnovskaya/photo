package com.vironit.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.vironit.domain.model.ProfileImage
import com.vironit.domain.model.User
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class UserEntity(
    @SerializedName("id") override val id: String,
    @SerializedName("first_name") override val firstName: String,
    @SerializedName("second_name") override val secondName: String,
    @SerializedName("name") override val name: String,
    @SerializedName("instagram_username") override val inst: String,
    @SerializedName("twitter_username") override val twitter: String,
    @SerializedName("portfolio_url") override val portfolio: String,
    @SerializedName("profile_image") override val profileImage: @RawValue ProfileImageEntity,
    @SerializedName("total_photos") override val totalPhotos: String,
    @SerializedName("total_collections") override val collection: String
) : Parcelable, User