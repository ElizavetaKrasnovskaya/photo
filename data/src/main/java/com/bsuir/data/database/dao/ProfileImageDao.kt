package com.bsuir.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bsuir.domain.database.model.ProfileImageEntity

@Dao
interface ProfileImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profileImageEntity: ProfileImageEntity)

    @Query("SELECT * FROM userProfileImage")
    suspend fun getAll(): List<ProfileImageEntity>
}