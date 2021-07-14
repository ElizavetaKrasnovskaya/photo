package com.vironit.data.mapper

import com.vironit.data.retrofit.model.UnsplashExif
import com.vironit.domain.mapper.Mapper
import com.vironit.domain.model.Exif

class ExifMapper : Mapper<UnsplashExif, Exif> {
    override fun fromEntity(from: UnsplashExif): Exif {
        return Exif(from.make, from.model, from.exposure, from.aperture, from.focal, from.iso)
    }

    override fun toEntity(from: Exif): UnsplashExif {
        return UnsplashExif(from.make, from.model, from.exposure, from.aperture, from.focal, from.iso)
    }
}