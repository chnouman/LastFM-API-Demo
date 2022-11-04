@file:OptIn(ExperimentalCoroutinesApi::class)

package com.chnouman.lastfmapidemo.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import androidx.test.runner.AndroidJUnit4
import com.chnouman.lastfmapidemo.DataGenerator
import com.chnouman.lastfmapidemo.data.local.dao.ArtistDao
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ArtistDaoTests {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: LastFmDatabase
    private lateinit var dao: ArtistDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LastFmDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.getArtistDao()
    }

    @Test
    fun basicTest() {
        assertThat(database).isNotNull()
        assertThat(dao).isNotNull()
    }

    @Test
    fun insertArtistSuccess() = runTest {
        val artist = DataGenerator.getArtist("ABC")
        dao.insert(artist)
        assertThat(dao.getArtist(artist.name)).isNotNull()
    }

    @Test
    fun insertAllArtistSuccess() = runTest {
        val artist = DataGenerator.getArtist("ABC")
        val artist2 = DataGenerator.getArtist("ABC2")
        dao.insert(artist)
        dao.insert(artist2)
        dao.getArtist(artist.name)
        assertThat(dao.getArtist(artist.name)).isNotNull()
        assertThat(dao.getArtist(artist2.name)).isNotNull()
    }

    @Test
    fun insertAllArtistFailure() = runTest {
        val artist = DataGenerator.getArtist("ABC")
        val artist2 = DataGenerator.getArtist("ABC")
        //same ids
        dao.insert(artist)
        dao.insert(artist2)
        dao.delete(artist.name)
        assertThat(dao.getArtist(artist.name)).isNull()
        assertThat(dao.getArtist(artist2.name)).isNull()
    }

    @After
    fun teardown() {
        database.close()
    }

}