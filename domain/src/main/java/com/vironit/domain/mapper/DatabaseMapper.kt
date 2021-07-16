package com.vironit.domain.mapper

interface DatabaseMapper<E, M> {
    fun fromEntity(from: E): M
    fun toEntity(from: M): E
}