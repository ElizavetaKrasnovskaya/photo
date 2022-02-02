package com.vironit.data.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.vironit.data.database.UnsplashDb
import com.vironit.data.database.dao.ExifDao
import com.vironit.domain.database.model.ExifEntity
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ExifDaoTest : TestCase() {

    private lateinit var database: UnsplashDb
    private lateinit var dao: ExifDao

    @Before
    public override fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            UnsplashDb::class.java
        ).allowMainThreadQueries().build()
        dao = database.exifDao
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertExif() = runBlocking {
        val exif = ExifEntity("make", "model", "exposure", "aperture", "focal", "iso", null)
        dao.insert(exif)
        val exifs = dao.getAll()
        assertThat(exifs).contains(exif)
    }

    @Test
    fun selectAll() = runBlocking {
        val exif1 = ExifEntity("make1", "model1", "exposure1", "aperture1", "focal1", "iso1", null)
        val exif2 = ExifEntity("make2", "model2", "exposure2", "aperture2", "focal2", "iso2", null)
        val exif3 = ExifEntity("make3", "model3", "exposure3", "aperture3", "focal3", "iso3", null)
        dao.insert(exif1)
        dao.insert(exif2)
        dao.insert(exif3)
        val exifs = dao.getAll()
        assertThat(exifs).contains(exif1)
        assertThat(exifs).contains(exif2)
        assertThat(exifs).contains(exif3)
    }
}
