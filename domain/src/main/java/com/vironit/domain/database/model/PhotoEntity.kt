package com.vironit.domain.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vironit.domain.retrofit.model.UnsplashPhoto

@Entity(
    tableName = "photo"
)
data class PhotoEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "width") val width: String?,
    @ColumnInfo(name = "height") val height: String?,
    @ColumnInfo(name = "color") val color: String?,
    @ColumnInfo(name = "alt_description") val description: String?,
    @ColumnInfo(name = "likes") val likes: String?,
    @ColumnInfo(name = "created_at") val date: String?
){
    fun convertToPhoto(): UnsplashPhoto {
        return UnsplashPhoto(
            this.id,
            this.width,
            this.height,
            this.color,
            this.description,
            null,
            null,
            this.likes,
            null,
            this.date
        )
    }
}
