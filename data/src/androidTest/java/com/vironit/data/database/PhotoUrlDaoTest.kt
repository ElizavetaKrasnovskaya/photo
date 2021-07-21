package com.vironit.data.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.vironit.data.database.dao.PhotoUrlDao
import com.vironit.domain.database.model.PhotoUrlEntity
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PhotoUrlDaoTest : TestCase() {

    private lateinit var database: UnsplashDb
    private lateinit var dao: PhotoUrlDao

    @Before
    public override fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            UnsplashDb::class.java
        ).allowMainThreadQueries().build()
        dao = database.photoUrlDao
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertPhotoUrl() = runBlocking {
        val photoUrl = PhotoUrlEntity("full", "regular", null)
        dao.insert(photoUrl)
        val photoUrls = dao.getAll()
        assertThat(photoUrls).contains(photoUrl)
    }

    @Test
    fun selectAll() = runBlocking {
        val photoUrl1 = PhotoUrlEntity("full1", "regular1", null)
        val photoUrl2 = PhotoUrlEntity("full2", "regular2", null)
        val photoUrl3 = PhotoUrlEntity("full3", "regular3", null)
        dao.insert(photoUrl1)
        dao.insert(photoUrl2)
        dao.insert(photoUrl3)
        val exifs = dao.getAll()
        assertThat(exifs).contains(photoUrl1)
        assertThat(exifs).contains(photoUrl2)
        assertThat(exifs).contains(photoUrl3)
    }
}