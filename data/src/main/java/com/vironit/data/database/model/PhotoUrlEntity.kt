package com.vironit.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.vironit.data.retrofit.model.UnsplashPhotoUrl

@Entity(
    tableName = "photoUrl",
    foreignKeys = [ForeignKey(
        entity = PhotoEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("photoId"),
        onDelete = ForeignKey.CASCADE
    )]
)
class PhotoUrlEntity(
    @ColumnInfo(name = "full") val full: String?,
    @ColumnInfo(name = "regular") val regular: String?,
    @ColumnInfo(name = "photoId") val photoId: String? = null
){
    @PrimaryKey(autoGenerate = true)
    var id = 0

    fun convertToPhotoUrl(): UnsplashPhotoUrl {
        return UnsplashPhotoUrl(
            this.full,
            this.regular
        )
    }
}