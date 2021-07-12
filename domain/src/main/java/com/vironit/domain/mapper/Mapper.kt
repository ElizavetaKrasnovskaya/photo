package com.vironit.domain.mapper

interface Mapper<E, M> {
    fun fromEntity(from: E): M
    fun toEntity(from: M): E
}