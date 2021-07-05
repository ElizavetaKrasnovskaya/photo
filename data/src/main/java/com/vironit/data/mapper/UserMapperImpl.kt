package com.vironit.data.mapper

import com.vironit.data.model.UserEntity
import com.vironit.domain.mapper.Mapper
import com.vironit.domain.model.User
import javax.inject.Inject

class UserMapperImpl @Inject constructor(private val mapper: ProfileImageMapperImpl) :
    Mapper<UserEntity, User> {
    override fun fromEntity(from: UserEntity): User {
        return User(
            from.id, from.username, from.name, mapper.fromEntity(from.profileImageEntity),
            from.totalPhotos, from.collection
        )
    }

    override fun toEntity(from: User): UserEntity {
        return UserEntity(
            from.id, from.username, from.name, mapper.toEntity(from.profileImage),
            from.totalPhotos, from.collection
        )
    }
}