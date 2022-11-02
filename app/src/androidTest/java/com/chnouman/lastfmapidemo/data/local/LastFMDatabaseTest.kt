@file:OptIn(ExperimentalCoroutinesApi::class)

package com.chnouman.lastfmapidemo.data.local

import android.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import androidx.test.runner.AndroidJUnit4
import com.chnouman.lastfmapidemo.data.local.dao.AlbumDao
import com.chnouman.lastfmapidemo.data.local.entities.Album
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
    fun basicTest(){

        assertThat(database).isNotNull()
        assertThat(dao).isNotNull()
    }

    @Test
    fun insertShoppingItemSuccess() = runTest {
        val shoppingItem = Album("ABC",0,"https://lastfm.com","https://lastfm.com","ABC",false)
        dao.insert(shoppingItem)
        assertThat(dao.getAll()).hasSize(1)
    }
    @Test
    fun insertShoppingItemFailure() = runTest {
        val shoppingItem = Album("ABC",0,"https://lastfm.com","https://lastfm.com","ABC",false)
        val shoppingItem2 = Album("ABC2",0,"https://lastfm.com","https://lastfm.com","ABC",false)
        dao.insert(shoppingItem)
        dao.insert(shoppingItem2)
        assertThat(dao.getAll().size).isEqualTo(2)
        assertThat(dao.getAll()).isNotEqualTo(1)
        assertThat(dao.getAll().size).isNotEqualTo(3)
    }

    @Test
    fun duplicateScenarioSuccess() = runTest {
        val shoppingItem = Album("ABC",0,"https://lastfm.com","https://lastfm.com","ABC",false)
        dao.insert(shoppingItem)
        dao.insert(shoppingItem)
        assertThat(dao.getAll().size).isEqualTo(1)
     }

    @Test
    fun duplicateScenarioFailure() = runTest {
        val shoppingItem = Album("ABC",0,"https://lastfm.com","https://lastfm.com","ABC",false)
        dao.insert(shoppingItem)
        dao.insert(shoppingItem)
        dao.insert(shoppingItem)
        assertThat(dao.getAll().size).isNotEqualTo(3)
        assertThat(dao.getAll().size).isNotEqualTo(2)
        assertThat(dao.getAll().size).isEqualTo(1)
     }


    @Test
    fun deleteScenarioSuccess() = runTest {
        val shoppingItem = Album("ABC",0,"https://lastfm.com","https://lastfm.com","ABC",false)
        dao.insert(shoppingItem)
        dao.delete(shoppingItem.name)
        assertThat(dao.getAll().size).isEqualTo(0)
     }

    @Test
    fun deleteScenarioFailure() = runTest {
        val shoppingItem = Album("ABC",0,"https://lastfm.com","https://lastfm.com","ABC_ARTIST",false)
        dao.insert(shoppingItem)
        dao.delete(shoppingItem.artistName)//Passing Wrong Name
        assertThat(dao.getAll().size).isEqualTo(1)
        assertThat(dao.getAll().size).isNotEqualTo(0)
     }

    @Test
    fun insertAllScenarioTest() = runTest {
        val shoppingItem = Album("ABC",0,"https://lastfm.com","https://lastfm.com","ABC_ARTIST",false)
        val shoppingItem2 = Album("ABC2",0,"https://lastfm.com","https://lastfm.com","ABC_ARTIST",false)
        dao.insertAll(mutableListOf(shoppingItem,shoppingItem2))
        assertThat(dao.getAll().size).isEqualTo(2)
     }

    @Test
    fun isExistScenarioSuccess() = runTest {
        val shoppingItem = Album("ABC",0,"https://lastfm.com","https://lastfm.com","ABC_ARTIST",false)
        dao.insert(shoppingItem)
        val exist = dao.isExist(shoppingItem.name)
        assertThat(exist).isTrue()
     }

    @Test
    fun isExistScenarioFailure() = runTest {
        val shoppingItem = Album("ABC",0,"https://lastfm.com","https://lastfm.com","ABC_ARTIST",false)
        dao.insert(shoppingItem)
        dao.delete(shoppingItem.name)
        val exist = dao.isExist(shoppingItem.name)
        assertThat(exist).isFalse()
     }

    @After
    fun teardown() {
        database.close()
    }
}