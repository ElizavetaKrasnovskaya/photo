package com.vironit.data.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.vironit.data.database.UnsplashDb
import com.vironit.data.database.dao.ProfileImageDao
import com.vironit.data.database.dao.UserDao
import com.vironit.domain.database.model.ProfileImageEntity
import com.vironit.domain.database.model.UserEntity
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDaoTest : TestCase() {

    private lateinit var database: UnsplashDb
    private lateinit var dao: UserDao

    @Before
    public override fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            UnsplashDb::class.java
        ).allowMainThreadQueries().build()
        dao = database.userDao
    }

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun insertUser() = runBlocking {
        val user = UserEntity("id", "first", "second", "name",
            "inst", "twitter", "portfolio", "total", "collections",
        null)
        dao.insert(user)
        val users = dao.getAll()
        assertThat(users).contains(user)
    }

    @Test
    fun selectAll() = runBlocking {
        val user1 = UserEntity("id1", "first1", "second1", "name1", "inst1",
            "twitter1", "portfolio1", "total1", "collections1",
            null)
        val user2 = UserEntity("id2", "first2", "second2", "name2", "inst2",
            "twitter2", "portfolio2", "total2", "collections2",
            null)
        val user3 = UserEntity("id3", "first3", "second3", "name3", "inst3",
            "twitter3", "portfolio3", "total3", "collections3",
            null)
        dao.insert(user1)
        dao.insert(user2)
        dao.insert(user3)
        val users = dao.getAll()
        assertThat(users).contains(user1)
        assertThat(users).contains(user2)
        assertThat(users).contains(user3)
    }
}