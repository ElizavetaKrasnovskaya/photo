package com.bsuir.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bsuir.domain.database.model.ExifEntity

@Dao
interface ExifDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exifEntity: ExifEntity)

    @Query("SELECT * FROM exif")
    suspend fun getAll(): List<ExifEntity>
}