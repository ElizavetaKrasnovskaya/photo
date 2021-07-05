package com.vironit.data.mapper

import com.vironit.data.model.PhotoEntity
import com.vironit.domain.mapper.Mapper
import com.vironit.domain.model.Photo
import javax.inject.Inject

class PhotoMapperImpl @Inject constructor(
    private val photoUrlMapperImpl: PhotoUrlMapperImpl,
    private val userMapperImpl: UserMapperImpl, private val sponsorshipMapperImpl: SponsorshipMapperImpl,
    private val exifMapperImpl: ExifMapperImpl
) : Mapper<PhotoEntity, Photo> {
    override fun fromEntity(from: PhotoEntity): Photo {
        return Photo(
            from.id,
            from.width,
            from.height,
            from.color,
            from.description,
            photoUrlMapperImpl.fromEntity(from.urlEntity),
            userMapperImpl.fromEntity(from.userEntity),
            from.likes,
            sponsorshipMapperImpl.fromEntity(from.sponsorshipEntity),
            exifMapperImpl.fromEntity(from.exifEntity)
        )
    }

    override fun toEntity(from: Photo): PhotoEntity {
        return PhotoEntity(
            from.id, from.width, from.height, from.color, from.description,
            photoUrlMapperImpl.toEntity(from.url), userMapperImpl.toEntity(from.user),
            from.likes, sponsorshipMapperImpl.toEntity(from.sponsorship),
            exifMapperImpl.toEntity(from.exif)
        )
    }
}