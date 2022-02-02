package com.bsuir.domain.mapper

interface DatabaseMapper<E, M> {
    fun fromEntity(from: E): M
    fun toEntity(from: M): E
}