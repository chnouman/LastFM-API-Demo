package com.chnouman.lastfmapidemo.presentation.main

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chnouman.lastfmapidemo.R
import com.chnouman.lastfmapidemo.core.utils.launchFragmentInHiltContainer
import com.chnouman.lastfmapidemo.presentation.main.adapter.AlbumListAdapter
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainFragmentTest {

    @Test
    fun checkAtStart_MainFragment_Loading() {
        // Create a TestNavHostController
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        // Create a graphical FragmentScenario for the TitleScreen
        launchFragmentInHiltContainer<MainFragment>(Bundle()) {
            navController.setGraph(R.navigation.main_nav_graph)
            navController.setCurrentDestination(R.id.mainFragment)
            Navigation.setViewNavController(requireView(), navController)
        }
        // Verify that performing a click changes the NavController’s state
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.mainFragment)
    }

    @Test
    fun checkClicking_RecyclerViewItem_Opens_DetailFragment() {
        // Create a TestNavHostController
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        // Create a graphical FragmentScenario for the TitleScreen
        launchFragmentInHiltContainer<MainFragment>(Bundle()) {
            navController.setGraph(R.navigation.main_nav_graph)
            navController.setCurrentDestination(R.id.mainFragment)
            Navigation.setViewNavController(requireView(), navController)
        }

        // Verify that performing a click changes the NavController’s state
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.mainFragment)
        onView(withId(R.id.albumsRecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<AlbumListAdapter.AlbumViewHolder>(
                0,
                click()
            )
        )
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.detailFragment)
    }
}
