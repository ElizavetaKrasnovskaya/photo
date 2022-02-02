package com.vironit.data.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.vironit.data.database.UnsplashDb
import com.vironit.data.database.dao.PhotoDao
import com.vironit.domain.database.model.PhotoAndUser
import com.vironit.domain.database.model.PhotoEntity
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PhotoDaoTest : TestCase() {

    private lateinit var database: UnsplashDb
    private lateinit var dao: PhotoDao

    @Before
    public override fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            UnsplashDb::class.java
        ).allowMainThreadQueries().build()
        dao = database.photoDao
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertPhoto() = runBlocking {
        val photoEntity = PhotoEntity(
            "id", "width", "height", "color", "description",
            "likes", "date"
        )
        val photo = PhotoAndUser(photoEntity, null, null, null)
        dao.insert(photoEntity)
        val photos = dao.getAll()
        assertThat(photos).contains(photo)
    }

    @Test
    fun deletePhoto() = runBlocking {
        val photoEntity = PhotoEntity(
            "id", "width", "height", "color", "description",
            "likes", "date"
        )
        val photo = PhotoAndUser(photoEntity, null, null, null)
        dao.insert(photoEntity)
        dao.delete(photoEntity.id)
        val photos = dao.getAll()
        assertThat(photos).doesNotContain(photo)
    }

    @Test
    fun getAllPhotos() = runBlocking {
        val photoEntity1 = PhotoEntity(
            "id1", "width1", "height1", "color1", "description1",
            "likes1", "date1"
        )
        val photoEntity2 = PhotoEntity(
            "id2", "width2", "height2", "color2", "description2",
            "likes2", "date2"
        )
        val photoEntity3 = PhotoEntity(
            "id3", "width3", "height3", "color3", "description3",
            "likes3", "date3"
        )
        val photo1 = PhotoAndUser(photoEntity1, null, null, null)
        val photo2 = PhotoAndUser(photoEntity2, null, null, null)
        val photo3 = PhotoAndUser(photoEntity3, null, null, null)
        dao.insert(photoEntity1)
        dao.insert(photoEntity2)
        dao.insert(photoEntity3)
        val photos = dao.getAll()
        assertThat(photos).contains(photo1)
        assertThat(photos).contains(photo2)
        assertThat(photos).contains(photo3)
    }

    @Test
    fun getPhotoById() = runBlocking {
        val photoEntity = PhotoEntity(
            "id", "width", "height", "color", "description",
            "likes", "date"
        )
        val photo = PhotoAndUser(photoEntity, null, null, null)
        dao.insert(photoEntity)
        val photoFromDatabase = dao.getById(photoEntity.id)
        assertThat(photoFromDatabase).isEqualTo(photo)
    }

    @Test
    fun searchPhoto() = runBlocking {
        val photoEntity = PhotoEntity(
            "id", "width", "height", "color", "description",
            "likes", "date"
        )
        val photo = PhotoAndUser(photoEntity, null, null, null)
        dao.insert(photoEntity)
        val photoFromDatabase = dao.searchPhoto(photoEntity.description!!)
        assertThat(photoFromDatabase).contains(photo)
    }
}