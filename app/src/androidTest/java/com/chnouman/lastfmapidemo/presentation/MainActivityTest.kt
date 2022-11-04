package com.chnouman.lastfmapidemo.presentation

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.chnouman.lastfmapidemo.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @Test
    fun testActivity_inView() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.main)).check(matches(isDisplayed()))
        onView(withId(R.id.toolbar)).check(matches(hasDescendant(withText(R.string.label_main_fragment))))
    }

    // Visibility
    @Test
    fun testVisibility_title_nextButton() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)

        /*  onView(withId(R.id.activity_main_title))
              .check(matches(isDisplayed())) // method 1

          onView(withId(R.id.activity_main_title))
              .check(matches(withEffectiveVisibility(Visibility.VISIBLE))) // method 2

          onView(withId(R.id.button_next_activity))
              .check(matches(isDisplayed()))*/
    }

    // Text
    @Test
    fun testEmptyTextDisplayed() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.searchFragment)).perform(click())

       /* onView(withId(R.id.activity_main_title))
            .check(matches(withText(R.string.text_mainactivity)))*/
    }

}

