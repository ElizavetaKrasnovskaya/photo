package com.vironit.domain.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class PhotoAndUser (
    @Embedded val photoEntity: PhotoEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "photoId",
        entity = UserEntity::class
    ) val user: UserAndLinks? = null,

    @Relation(
        parentColumn = "id",
        entityColumn = "photoId",
        entity = ExifEntity::class
    ) val exifEntity: ExifEntity? = null,

    @Relation(
        parentColumn = "id",
        entityColumn = "photoId",
        entity = PhotoUrlEntity::class
    ) val photoUrlEntity: PhotoUrlEntity? = null
)