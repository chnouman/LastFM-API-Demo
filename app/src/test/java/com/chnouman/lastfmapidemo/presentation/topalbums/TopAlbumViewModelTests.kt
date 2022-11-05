package com.chnouman.lastfmapidemo.presentation.topalbums

import app.cash.turbine.test
import com.chnouman.lastfmapidemo.data.local.entities.Album
import com.chnouman.lastfmapidemo.data.local.entities.Artist
import com.chnouman.lastfmapidemo.data.repository.FakeTopAlbumRepository
import com.chnouman.lastfmapidemo.domain.usecases.album.CompareLocalAlbums
import com.chnouman.lastfmapidemo.domain.usecases.album.GetAlbumInfo
import com.chnouman.lastfmapidemo.domain.usecases.album.GetTopAlbums
import com.chnouman.lastfmapidemo.domain.usecases.album.AddAlbum
import com.chnouman.lastfmapidemo.domain.usecases.album.DeleteAlbum
import com.chnouman.lastfmapidemo.domain.usecases.artist.AddArtist
import com.chnouman.lastfmapidemo.domain.usecases.artist.DeleteArtist
import com.chnouman.lastfmapidemo.domain.usecases.track.AddTrack
import com.chnouman.lastfmapidemo.domain.usecases.track.DeleteTracks
import com.chnouman.lastfmapidemo.presentation.topalbum.viewmodel.TopAlbumViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class TopAlbumViewModelTests {

    private lateinit var topAlbumViewModel: TopAlbumViewModel
    private lateinit var topAlbumRepository: FakeTopAlbumRepository

    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        topAlbumRepository = FakeTopAlbumRepository()
        topAlbumViewModel = TopAlbumViewModel(
            AddTrack(topAlbumRepository),
            GetAlbumInfo(topAlbumRepository),
            GetTopAlbums(topAlbumRepository),
            CompareLocalAlbums(topAlbumRepository),
            DeleteAlbum(topAlbumRepository),
            AddAlbum(topAlbumRepository),
            AddArtist(topAlbumRepository),
            DeleteTracks(topAlbumRepository),
            DeleteArtist(topAlbumRepository)
        )
    }

    @Test
    fun getTopAlbums_Success() = runTest {
        topAlbumViewModel.getTopAlbums("john")
        topAlbumViewModel.eventFlow.test {
            assertThat(awaitItem()).isInstanceOf(TopAlbumViewModel.UIEvent.Loading::class.java)
            val awaitItem = awaitItem()
            assertThat(awaitItem).isInstanceOf(TopAlbumViewModel.UIEvent.Success::class.java)
            assertThat((awaitItem as TopAlbumViewModel.UIEvent.Success).artists).hasSize(1)
        }
    }

    @Test
    fun saveAlbum_Success() = runTest {
        topAlbumViewModel.saveAlbum(
            1,
            Album("ABC", 200, "", "", "", false),
            Artist("ABC", "", "", "")
        )
        topAlbumViewModel.eventFlow.test {
            assertThat(awaitItem()).isInstanceOf(TopAlbumViewModel.UIEvent.ItemSaved::class.java)
        }
    }

    @Test
    fun saveAlbum_Failure() = runTest {
        topAlbumViewModel.saveAlbum(
            1,
            Album("ABC", 200, "", "", "", false),
            Artist("ABC", "", "", "")
        )
        topAlbumViewModel.eventFlow.test {
            awaitItem().let {
                assertThat(it).isNotInstanceOf(TopAlbumViewModel.UIEvent.ItemDeleted::class.java)
                assertThat(it).isNotInstanceOf(TopAlbumViewModel.UIEvent.Loading::class.java)
                assertThat(it).isNotInstanceOf(TopAlbumViewModel.UIEvent.Success::class.java)
                assertThat(it).isNotInstanceOf(TopAlbumViewModel.UIEvent.Error::class.java)
            }
        }
    }

    @Test
    fun deleteAlbum_Success() = runTest {
        topAlbumViewModel.deleteAlbum(Album("ABC", 200, "", "", "", false), 1)
        topAlbumViewModel.eventFlow.test {
            awaitItem().let {
                assertThat(it).isInstanceOf(TopAlbumViewModel.UIEvent.ItemDeleted::class.java)
            }
        }
    }

    @Test
    fun deleteAlbum_Failure() = runTest {
        topAlbumViewModel.deleteAlbum(Album("ABC", 200, "", "", "", false), 1)
        topAlbumViewModel.eventFlow.test {
            awaitItem().let {
                assertThat(it).isNotInstanceOf(TopAlbumViewModel.UIEvent.ItemSaved::class.java)
                assertThat(it).isNotInstanceOf(TopAlbumViewModel.UIEvent.Loading::class.java)
                assertThat(it).isNotInstanceOf(TopAlbumViewModel.UIEvent.Success::class.java)
                assertThat(it).isNotInstanceOf(TopAlbumViewModel.UIEvent.Error::class.java)
            }
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}