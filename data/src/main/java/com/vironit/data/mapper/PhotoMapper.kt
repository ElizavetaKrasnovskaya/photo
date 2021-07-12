package com.vironit.data.mapper

import com.vironit.data.model.PhotoEntity
import com.vironit.domain.mapper.Mapper
import com.vironit.domain.model.unsplash.Photo
import javax.inject.Inject

class PhotoMapper @Inject constructor(
    val photoUrlMapper: PhotoUrlMapper, val userMapper: UserMapper,
    val sponsorshipMapper: SponsorshipMapper, val exifMapper: ExifMapper
) : Mapper<PhotoEntity, Photo> {
    override fun fromEntity(from: PhotoEntity): Photo {
        return Photo(
            from.id,
            from.width,
            from.height,
            from.color,
            from.description,
            from.url?.let { photoUrlMapper.fromEntity(it) },
            from.user?.let { userMapper.fromEntity(it) },
            from.likes,
            from.sponsorship?.let { sponsorshipMapper.fromEntity(it) },
            from.exif?.let { exifMapper.fromEntity(it) },
            from.date
        )
    }

    override fun toEntity(from: Photo): PhotoEntity {
        return PhotoEntity(
            from.id, from.width, from.height, from.color, from.description,
            from.url?.let { photoUrlMapper.toEntity(it) },
            from.user?.let { userMapper.toEntity(it) }, from.likes,
            from.sponsorship?.let { sponsorshipMapper.toEntity(it) },
            from.exif?.let { exifMapper.toEntity(it) }, from.date
        )
    }
}