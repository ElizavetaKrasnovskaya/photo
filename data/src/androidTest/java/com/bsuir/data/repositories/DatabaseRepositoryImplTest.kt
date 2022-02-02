package com.vironit.data.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.vironit.data.database.UnsplashDb
import com.vironit.data.repository.DatabaseRepositoryImpl
import com.vironit.domain.database.model.*
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DatabaseRepositoryImplTest : TestCase() {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private lateinit var database: UnsplashDb
    private lateinit var repository: DatabaseRepositoryImpl
    val exif = ExifEntity("make", "model", "exposure", "aperture", "focal", "iso", null)
    val user = UserEntity(
        "id", "first", "second", "name",
        "inst", "twitter", "portfolio", "total", "collections",
        null
    )
    val photoUrl = PhotoUrlEntity("full", "regular", null)
    val profileImage = ProfileImageEntity("small", "medium", "large")

    @Before
    public override fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            UnsplashDb::class.java
        ).allowMainThreadQueries().build()
        repository = DatabaseRepositoryImpl(database)
    }

    @Test
    fun insertExifTest() = runBlocking {
        repository.insertExif(exif)
        assertThat(repository.getAllExif()).isEqualTo(listOf(exif))
    }

    @Test
    fun insertUserTest() = runBlocking {
        repository.insertUser(user)
        assertThat(repository.getAllUsers()).isEqualTo(listOf(user))
    }

    @Test
    fun insertPhotoUrlTest() = runBlocking {
        repository.insertPhotoUrl(photoUrl)
        assertThat(repository.getAllPhotoUrl()).isEqualTo(listOf(photoUrl))
    }

    @Test
    fun insertProfileImageTest() = runBlocking {
        repository.insertProfileImage(profileImage)
        assertThat(repository.getAllProfileImage()).isEqualTo(listOf(profileImage))
    }

    @Test
    fun insertPhotoTest() = runBlocking {
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
        repository.insertPhoto(photoEntity1)
        repository.insertPhoto(photoEntity2)
        repository.insertPhoto(photoEntity3)
        assertThat(repository.getAllPhotos()).isEqualTo(listOf(photo1, photo2, photo3))
    }

    @Test
    fun deletePhotoTest() = runBlocking {
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
        repository.insertPhoto(photoEntity1)
        repository.insertPhoto(photoEntity2)
        repository.insertPhoto(photoEntity3)
        repository.deletePhoto("id3")
        assertThat(repository.getAllPhotos()).isEqualTo(listOf(photo1, photo2))
    }
}