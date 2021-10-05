package com.severianfw.githubuser.view

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.severianfw.githubuser.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    private val dummyUsername = "severianfw"

    @Before
    fun setup() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun openFavoriteUserPage() {
        onView(withId(R.id.ic_favourite)).perform(click())
    }

    @Test
    fun searchUser() {
        onView(withId(R.id.user_search_bar)).perform(typeText(dummyUsername), pressKey(66))
        onView(withId(R.id.progress_bar)).check(matches(isDisplayed()))
    }
}