package com.vironit.data.mapper

import com.vironit.data.model.ProfileImageEntity
import com.vironit.domain.mapper.Mapper
import com.vironit.domain.model.ProfileImage

class ProfileImageMapperImpl: Mapper<ProfileImageEntity, ProfileImage> {
    override fun fromEntity(from: ProfileImageEntity): ProfileImage {
        return ProfileImage(from.small, from.medium, from.large)
    }

    override fun toEntity(from: ProfileImage): ProfileImageEntity {
        return ProfileImageEntity(from.small, from.medium, from.large)
    }
}