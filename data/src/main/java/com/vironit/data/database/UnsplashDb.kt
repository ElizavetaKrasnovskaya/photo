package com.vironit.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vironit.data.database.dao.*
import com.vironit.domain.database.model.*

@Database(
    entities = [
        ExifEntity::class,
        PhotoEntity::class,
        PhotoUrlEntity::class,
        ProfileImageEntity::class,
        UserEntity::class,
        SearchEntity::class
    ],
    version = 1
)
abstract class UnsplashDb : RoomDatabase() {

    abstract val exifDao: ExifDao
    abstract val photoDao: PhotoDao
    abstract val photoUrlDao: PhotoUrlDao
    abstract val profileImageDao: ProfileImageDao
    abstract val userDao: UserDao
    abstract val searchDao: SearchDao

    companion object {
        @Volatile
        private var INSTANCE: UnsplashDb? = null

        fun getInstance(context: Context): UnsplashDb {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    UnsplashDb::class.java,
                    "check2"
                ).build()
            }
        }
    }
}