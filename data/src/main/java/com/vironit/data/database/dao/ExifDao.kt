package com.vironit.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.vironit.data.database.model.ExifEntity

@Dao
interface ExifDao {

    @Insert
    suspend fun insert(exifEntity: ExifEntity)
}