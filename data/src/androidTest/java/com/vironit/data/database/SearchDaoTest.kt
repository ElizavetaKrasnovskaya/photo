package com.vironit.data.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.vironit.data.database.dao.SearchDao
import com.vironit.domain.database.model.SearchEntity
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchDaoTest : TestCase() {

    private lateinit var database: UnsplashDb
    private lateinit var dao: SearchDao

    @Before
    public override fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            UnsplashDb::class.java
        ).allowMainThreadQueries().build()
        dao = database.searchDao
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertSearch() = runBlocking {
        val search = SearchEntity("query", "total", "date", false)
        dao.insert(search)
        val searches = dao.getAll()
        assertThat(searches).contains(search)
    }

    @Test
    fun deleteSearch() = runBlocking {
        val search = SearchEntity("query", "total", "date", false)
        search.id = 1
        dao.insert(search)
        dao.delete(search.id)
        val searches = dao.getAll()
        assertThat(searches).doesNotContain(search)
    }

    @Test
    fun getAllSearches() = runBlocking {
        val search1 = SearchEntity("query1", "total1", "date1", false)
        val search2 = SearchEntity("query2", "total2", "date2", false)
        val search3 = SearchEntity("query3", "total3", "date3", false)
        dao.insert(search1)
        dao.insert(search2)
        dao.insert(search3)
        val searches = dao.getAll()
        assertThat(searches).contains(search1)
        assertThat(searches).contains(search2)
        assertThat(searches).contains(search3)
    }

    @Test
    fun getFavouriteSearch() = runBlocking {
        val search = SearchEntity("query", "total", "date", true)
        dao.insert(search)
        dao.getFavourite()
        val searches = dao.getAll()
        assertThat(searches).contains(search)
    }

    @Test
    fun update() = runBlocking {
        val search = SearchEntity("query", "total", "date", false)
        search.id = 1
        dao.insert(search)
        dao.update(1, 1)
        val searchList = dao.getAll()
        assertThat(searchList[0].isFavourite).isEqualTo(true)
    }
}