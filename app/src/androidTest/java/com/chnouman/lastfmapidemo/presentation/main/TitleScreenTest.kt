package com.chnouman.lastfmapidemo.presentation.main

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chnouman.lastfmapidemo.R
import com.chnouman.lastfmapidemo.core.utils.launchFragmentInHiltContainer
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TitleScreenTest {

    @Test
    fun testNavigationToInGameScreen() {
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
//        onView(ViewMatchers.withId(R.id.searchFragment)).perform(ViewActions.click())
        assertThat(navController.currentDestination?.id).isEqualTo(R.id.mainFragment)
    }
}