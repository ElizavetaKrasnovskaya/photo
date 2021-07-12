package com.vironit.data.mapper

import com.vironit.data.model.PhotoUrlEntity
import com.vironit.domain.mapper.Mapper
import com.vironit.domain.model.unsplash.PhotoUrl

class PhotoUrlMapper : Mapper<PhotoUrlEntity, PhotoUrl> {
    override fun fromEntity(from: PhotoUrlEntity): PhotoUrl {
        return PhotoUrl(from.full, from.regular)
    }

    override fun toEntity(from: PhotoUrl): PhotoUrlEntity {
        return PhotoUrlEntity(from.full, from.regular)
    }
}