package com.vironit.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vironit.domain.database.model.PhotoAndUser
import com.vironit.domain.database.model.PhotoEntity

@Dao
interface PhotoDao {
    @Query("SELECT * FROM photo")
    suspend fun getAll(): List<PhotoAndUser>

    @Query("SELECT * FROM photo WHERE id = :photoId")
    suspend fun getById(photoId: String): PhotoAndUser

    @Query("SELECT * FROM photo WHERE alt_description LIKE :query")
    suspend fun searchPhoto(query: String): List<PhotoAndUser>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photoEntity: PhotoEntity)

    @Query("DELETE FROM photo WHERE id = :photoId")
    suspend fun delete(photoId: String)
}