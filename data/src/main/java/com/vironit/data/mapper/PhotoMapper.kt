package com.vironit.data.mapper

import com.vironit.data.retrofit.model.UnsplashPhoto
import com.vironit.domain.mapper.Mapper
import com.vironit.domain.model.Photo
import javax.inject.Inject

//class PhotoMapper @Inject constructor(
//    val photoUrlMapper: PhotoUrlMapper, val userMapper: UserMapper,
//    val sponsorshipMapper: SponsorshipMapper, val exifMapper: ExifMapper
//) : Mapper<UnsplashPhoto, Photo> {
//    override fun fromEntity(from: UnsplashPhoto): Photo {
//        return Photo(
//            from.id,
//            from.width,
//            from.height,
//            from.color,
//            from.description,
//            from.urlUnsplash?.let { photoUrlMapper.fromEntity(it) },
//            from.unsplashUser?.let { userMapper.fromEntity(it) },
//            from.likes,
//            from.unsplashSponsorship?.let { sponsorshipMapper.fromEntity(it) },
//            from.unsplashExif?.let { exifMapper.fromEntity(it) },
//            from.date
//        )
//    }
//
//    override fun toEntity(from: Photo): UnsplashPhoto {
//        return UnsplashPhoto(
//            from.id, from.width, from.height, from.color, from.description,
//            from.url?.let { photoUrlMapper.toEntity(it) },
//            from.user?.let { userMapper.toEntity(it) }, from.likes,
//            from.sponsorship?.let { sponsorshipMapper.toEntity(it) },
//            from.exif?.let { exifMapper.toEntity(it) }, from.date
//        )
//    }
//}