package com.vironit.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.vironit.data.database.model.UserEntity

@Dao
interface UserDao {
    @Insert
    suspend fun insert(userEntity: UserEntity)
}