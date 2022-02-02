package com.bsuir.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bsuir.domain.database.model.SearchEntity

@Dao
interface SearchDao {
    @Query("SELECT * FROM search")
    suspend fun getAll(): List<SearchEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(searchEntity: SearchEntity)

    @Query("DELETE FROM search WHERE id=:searchId")
    suspend fun delete(searchId: Int)

    @Query("SELECT * FROM search where isFavourite=1")
    suspend fun getFavourite(): List<SearchEntity>

    @Query("UPDATE search SET isFavourite=:isFavourite WHERE id=:id")
    suspend fun update(id: Int, isFavourite: Int)
}