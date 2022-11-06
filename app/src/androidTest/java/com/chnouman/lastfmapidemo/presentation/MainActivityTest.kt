package com.chnouman.lastfmapidemo.presentation

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.chnouman.lastfmapidemo.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @Test
    fun verifyTitle_is_Correct() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.main)).check(matches(isDisplayed()))
        onView(withId(R.id.toolbar)).check(matches(hasDescendant(withText(R.string.label_main_fragment))))
    }

    @Test
    fun testVisibility_title_nextButton() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun verifyOptionMenu_is_Displayed() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.searchFragment)).check(matches(isDisplayed()))
        onView(withId(R.id.searchFragment)).perform(click())
    }
}
