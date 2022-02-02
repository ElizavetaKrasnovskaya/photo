package com.bsuir.domain.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserAndLinks(
    @Embedded val userEntity: UserEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId",
        entity = ProfileImageEntity::class
    ) val profileImageEntity: ProfileImageEntity? = null
)