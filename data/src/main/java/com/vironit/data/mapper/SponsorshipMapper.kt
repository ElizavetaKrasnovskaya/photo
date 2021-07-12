package com.vironit.data.mapper

import com.vironit.data.model.SponsorshipEntity
import com.vironit.domain.mapper.Mapper
import com.vironit.domain.model.unsplash.Sponsorship

class SponsorshipMapper : Mapper<SponsorshipEntity, Sponsorship> {
    override fun fromEntity(from: SponsorshipEntity): Sponsorship {
        return Sponsorship(from.tagline)
    }

    override fun toEntity(from: Sponsorship): SponsorshipEntity {
        return SponsorshipEntity(from.tagline)
    }
}