package com.vironit.domain.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.vironit.domain.retrofit.model.UnsplashProfileImage
import com.vironit.domain.retrofit.model.UnsplashUser

@Entity(
    tableName = "user",
    foreignKeys = [ForeignKey(
        entity = PhotoEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("photoId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class UserEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "first_name") val firstName: String?,
    @ColumnInfo(name = "second_name") val secondName: String?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "instagram_username") val inst: String?,
    @ColumnInfo(name = "twitter_username") val twitter: String?,
    @ColumnInfo(name = "portfolio_url") val portfolio: String?,
    @ColumnInfo(name = "total_photos") val totalPhotos: String?,
    @ColumnInfo(name = "total_collections") val collection: String?,
    @ColumnInfo(name= "photoId") val photoId: String? = null
){
    fun convertToUser(profileImage: UnsplashProfileImage): UnsplashUser {
        return UnsplashUser(
            this.id,
            this.firstName,
            this.secondName,
            this.name,
            this.inst,
            this.twitter,
            this.portfolio,
            profileImage,
            this.totalPhotos,
            this.collection
        )
    }
}