package com.vironit.domain.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.vironit.domain.retrofit.model.UnsplashExif

@Entity(
    tableName = "exif",
    foreignKeys = [ForeignKey(
        entity = PhotoEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("photoId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class ExifEntity(
    @ColumnInfo(name = "make") val make: String?,
    @ColumnInfo(name = "model") val model: String?,
    @ColumnInfo(name = "exposure_time") val exposure: String?,
    @ColumnInfo(name = "aperture") val aperture: String?,
    @ColumnInfo(name = "focal_length") val focal: String?,
    @ColumnInfo(name = "iso") val iso: String?,
    @ColumnInfo(name = "photoId") val photoId: String? = null
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    fun convertToExif(): UnsplashExif {
        return UnsplashExif(
            this.make,
            this.model,
            this.exposure,
            this.aperture,
            this.focal,
            this.iso
        )
    }
}