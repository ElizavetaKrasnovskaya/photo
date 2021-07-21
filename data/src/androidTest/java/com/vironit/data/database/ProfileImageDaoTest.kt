package com.vironit.data.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.vironit.data.database.dao.ProfileImageDao
import com.vironit.domain.database.model.ProfileImageEntity
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileImageDaoTest : TestCase() {

    private lateinit var database: UnsplashDb
    private lateinit var dao: ProfileImageDao

    @Before
    public override fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            UnsplashDb::class.java
        ).allowMainThreadQueries().build()
        dao = database.profileImageDao
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertProfileImage() = runBlocking {
        val profileImage = ProfileImageEntity("small", "medium", "large")
        dao.insert(profileImage)
        val profileImages = dao.getAll()
        assertThat(profileImages).contains(profileImage)
    }

    @Test
    fun selectAll() = runBlocking {
        val profileImage1 = ProfileImageEntity("small1", "medium1", "large1")
        val profileImage2 = ProfileImageEntity("small2", "medium2", "large2")
        val profileImage3 = ProfileImageEntity("small3", "medium3", "large3")
        dao.insert(profileImage1)
        dao.insert(profileImage2)
        dao.insert(profileImage3)
        val profileImages = dao.getAll()
        assertThat(profileImages).contains(profileImage1)
        assertThat(profileImages).contains(profileImage2)
        assertThat(profileImages).contains(profileImage3)
    }
}