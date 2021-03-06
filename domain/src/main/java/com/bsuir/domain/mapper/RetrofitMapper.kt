package com.bsuir.domain.mapper

interface RetrofitMapper<E, M> {
    fun fromUnsplash(from: E): M
    fun toUnsplash(from: M): E
}