package com.chnouman.lastfmapidemo.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import androidx.test.runner.AndroidJUnit4
import com.chnouman.lastfmapidemo.BuildConfig
import com.chnouman.lastfmapidemo.di.NetworkModule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.HttpException
import retrofit2.Retrofit

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@SmallTest
class RetrofitClientTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private var instance: Retrofit? = RetrofitClient().retrofit
    private var lastFMApi: LastFMApi? = null

    @Before
    fun setup() {
        //Get an instance of LastFMApi using the Retrofit instance
        lastFMApi = instance?.create(LastFMApi::class.java)
    }

    @Test
    fun retrofit_InstanceBaseUrl() {
        //Assert that, Retrofit's base url matches to our BASE_URL
        assert(instance?.baseUrl().toString() == NetworkModule.BASE_URL)
    }

    @Test
    fun searchApi_ValidToken() = runTest {
        //Execute the API call
        val response = lastFMApi?.searchArtist("Justin", BuildConfig.API_KEY)
        assert(response != null)
    }

    @Test
    fun searchApi_InvalidToken() = runTest {
        //Execute the API call
        try {
            val response = lastFMApi?.searchArtist("Justin", "apiKey_Wrong")
            assert(response != null)
        } catch (e: HttpException) {
            assertThat(e.code()).isEqualTo(403)
        }
    }

    @Test
    fun searchApi_Success() = runTest {
        //Execute the API call
        val response = lastFMApi?.searchArtist("Justin", BuildConfig.API_KEY)
        assertThat(response?.results?.artistmatches?.artist?.size).isGreaterThan(0)
    }

    @Test
    fun getTopAlbum_Success() = runTest {
        //Execute the API call
        val response = lastFMApi?.getTopAlbums("Justin", BuildConfig.API_KEY)
        assert(response != null)
        assertThat(response?.topalbums?.album?.size).isGreaterThan(0)
    }

    @Test
    fun getAlbumInfo_Success() = runTest {
        //Execute the API call
        val response = lastFMApi?.getAlbumInfo("John Mayer","Heavier Things", BuildConfig.API_KEY)
        assert(response != null)
        assertThat(response?.album?.tracks?.tracks?.size).isGreaterThan(0)
    }

    @After
    fun tearDown() {
        instance = null
        lastFMApi = null
    }
}