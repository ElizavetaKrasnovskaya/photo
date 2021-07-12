package com.vironit.data.mapper

import com.vironit.data.model.ExifEntity
import com.vironit.domain.mapper.Mapper
import com.vironit.domain.model.unsplash.Exif

class ExifMapper : Mapper<ExifEntity, Exif> {
    override fun fromEntity(from: ExifEntity): Exif {
        return Exif(from.make, from.model, from.exposure, from.aperture, from.focal, from.iso)
    }

    override fun toEntity(from: Exif): ExifEntity {
        return ExifEntity(from.make, from.model, from.exposure, from.aperture, from.focal, from.iso)
    }
}