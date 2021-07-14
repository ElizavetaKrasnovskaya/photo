package com.vironit.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.vironit.data.database.model.PhotoUrlEntity

@Dao
interface PhotoUrlDao {
    @Insert
    suspend fun insert(photoUrlEntity: PhotoUrlEntity)
}