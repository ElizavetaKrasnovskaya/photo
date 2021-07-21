package com.vironit.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vironit.domain.database.model.PhotoUrlEntity

@Dao
interface PhotoUrlDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photoUrlEntity: PhotoUrlEntity)

    @Query("SELECT * FROM photoUrl")
    suspend fun getAll(): List<PhotoUrlEntity>
}