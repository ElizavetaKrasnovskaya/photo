package com.vironit.data.mapper

import com.vironit.data.retrofit.model.UnsplashUser
import com.vironit.domain.mapper.Mapper
import com.vironit.domain.model.User
import javax.inject.Inject

//class UserMapper @Inject constructor(val profileImageMapper: ProfileImageMapper) :
//    Mapper<UnsplashUser, User> {
//    override fun fromEntity(from: UnsplashUser): User {
//        return User(
//            from.id,
//            from.firstName,
//            from.secondName,
//            from.name,
//            from.inst,
//            from.twitter,
//            from.portfolio,
//            from.unsplashProfileImage?.let { profileImageMapper.fromEntity(it) },
//            from.totalPhotos,
//            from.collection
//        )
//    }
//
//    override fun toEntity(from: User): UnsplashUser {
//        return UnsplashUser(
//            from.id,
//            from.firstName,
//            from.secondName,
//            from.name,
//            from.inst,
//            from.twitter,
//            from.portfolio,
//            from.profileImage?.let { profileImageMapper.toEntity(it) },
//            from.totalPhotos,
//            from.collection
//        )
//    }
//}
