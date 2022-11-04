@file:OptIn(ExperimentalCoroutinesApi::class)

package com.chnouman.lastfmapidemo.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import androidx.test.runner.AndroidJUnit4
import com.chnouman.lastfmapidemo.DataGenerator
import com.chnouman.lastfmapidemo.data.local.dao.TrackDao
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
class TrackDaoTests {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: LastFmDatabase
    private lateinit var dao: TrackDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LastFmDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.getTrackDao()
    }

    @Test
    fun basicTest() {
        assertThat(database).isNotNull()
        assertThat(dao).isNotNull()
    }

    @Test
    fun insertTrackSuccess() = runTest {
        val track = DataGenerator.getTrack("ABC")
        dao.insert(track)
        assertThat(dao.getAll("albumName")).hasSize(1)
    }

    @Test
    fun insertTrackFailure() = runTest {
        val track = DataGenerator.getTrack("ABC")
        val track2 = DataGenerator.getTrack("ABC2")
        dao.insert(track)
        dao.insert(track2)
        assertThat(dao.getAll("albumName").size).isEqualTo(2)
        assertThat(dao.getAll("albumName")).isNotEqualTo(1)
        assertThat(dao.getAll("albumName").size).isNotEqualTo(3)
    }

    @Test
    fun duplicateTrackScenarioSuccess() = runTest {
        val track = DataGenerator.getTrack("ABC")
        dao.insert(track)
        dao.insert(track)
        assertThat(dao.getAll("albumName").size).isEqualTo(1)
    }

    @Test
    fun duplicateTrackScenarioFailure() = runTest {
        val track = DataGenerator.getTrack("ABC")
        dao.insert(track)
        dao.insert(track)
        dao.insert(track)
        assertThat(dao.getAll("albumName").size).isNotEqualTo(3)
        assertThat(dao.getAll("albumName").size).isNotEqualTo(2)
        assertThat(dao.getAll("albumName").size).isEqualTo(1)
    }


    @Test
    fun deleteTrackScenarioSuccess() = runTest {
        val track = DataGenerator.getTrack("ABC")
        dao.insert(track)
        dao.delete(track.albumName)
        assertThat(dao.getAll(track.albumName).size).isEqualTo(0)
    }

    @Test
    fun deleteTrackScenarioFailure() = runTest {
        val track = DataGenerator.getTrack("ABC")
        dao.insert(track)
        dao.delete(track.name)//Passing Wrong Name
        assertThat(dao.getAll(track.albumName).size).isEqualTo(1)
        assertThat(dao.getAll(track.albumName).size).isNotEqualTo(0)
    }

    @Test
    fun insertTrackAllScenarioTest() = runTest {
        val track = DataGenerator.getTrack("ABC")
        val track2 = DataGenerator.getTrack("ABC2")
        dao.insertAll(mutableListOf(track, track2))
        assertThat(dao.getAll("albumName").size).isEqualTo(2)
    }

    @After
    fun teardown() {
        database.close()
    }

}