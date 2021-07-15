package com.vironit.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.vironit.data.database.model.ExifEntity

@Dao
interface ExifDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exifEntity: ExifEntity)
}