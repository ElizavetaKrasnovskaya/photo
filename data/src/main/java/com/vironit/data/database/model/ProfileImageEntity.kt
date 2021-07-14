package com.vironit.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.vironit.data.retrofit.model.UnsplashProfileImage

@Entity(
    tableName = "userProfileImage",
    foreignKeys = [ForeignKey(
        entity = UserEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("userId"),
        onDelete = ForeignKey.CASCADE
    )]
)
class ProfileImageEntity(
    @ColumnInfo(name = "small") val small: String?,
    @ColumnInfo(name = "medium") val medium: String?,
    @ColumnInfo(name = "large") val large: String?,
    @ColumnInfo(name = "userId") val userId: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    fun convertToProfileImage(): UnsplashProfileImage {
        return UnsplashProfileImage(
            this.small,
            this.medium,
            this.large
        )
    }
}