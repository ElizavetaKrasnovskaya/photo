package com.vironit.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.vironit.data.database.model.ProfileImageEntity


@Dao
interface ProfileImageDao {
    @Insert
    suspend fun insert(profileImageEntity: ProfileImageEntity)
}