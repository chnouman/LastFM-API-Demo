package com.chnouman.lastfmapidemo.data.local

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import androidx.test.runner.AndroidJUnit4
import com.chnouman.lastfmapidemo.data.local.dao.AlbumDao
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class LastFMDatabaseTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: LastFmDatabase
    private lateinit var dao: AlbumDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LastFmDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.getAlbumDao()
    }

    @Test
    fun test(){

        assertThat(database).isNotNull()
        assertThat(dao).isNotNull()
    }

    @After
    fun teardown() {
        database.close()
    }
}