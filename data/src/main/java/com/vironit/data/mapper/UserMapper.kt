package com.vironit.data.mapper

import com.vironit.data.model.UserEntity
import com.vironit.domain.mapper.Mapper
import com.vironit.domain.model.unsplash.User
import javax.inject.Inject

class UserMapper @Inject constructor(val profileImageMapper: ProfileImageMapper) :
    Mapper<UserEntity, User> {
    override fun fromEntity(from: UserEntity): User {
        return User(
            from.id,
            from.firstName,
            from.secondName,
            from.name,
            from.inst,
            from.twitter,
            from.portfolio,
            from.profileImage?.let { profileImageMapper.fromEntity(it) },
            from.totalPhotos,
            from.collection
        )
    }

    override fun toEntity(from: User): UserEntity {
        return UserEntity(
            from.id,
            from.firstName,
            from.secondName,
            from.name,
            from.inst,
            from.twitter,
            from.portfolio,
            from.profileImage?.let { profileImageMapper.toEntity(it) },
            from.totalPhotos,
            from.collection
        )
    }
}
