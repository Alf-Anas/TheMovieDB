package com.lofrus.themoviedb

import android.os.SystemClock
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.core.AllOf.allOf
import org.hamcrest.core.IsNot.not
import org.junit.Before
import org.junit.Test

class InstrumentalActivityTest {

    private val waitingTimeAPI: Long = 5000
    private val waitingTime: Long = 1000

    @Before
    fun setup(){
        ActivityScenario.launch(SplashScreenActivity::class.java)
    }

    @Test
    fun testOnCreate() {
        onView(withId(R.id.splTVVersion)).check(matches(isDisplayed()))
        onView(withId(R.id.splTVVersion)).check(matches(withText("© Submission 1 Dicoding - Android Jetpack Pro")))
        SystemClock.sleep(waitingTimeAPI) //Wait till MainActivity Started

        onView(withId(R.id.mainTabLayout)).check(matches(isDisplayed()))
        onView(withId(R.id.mainViewPager)).check(matches(isDisplayed()))

        SystemClock.sleep(waitingTimeAPI) //Wait Data Get
        onView(allOf(withId(R.id.moviesProgressBar), not(isDisplayed())))
        onView(withId(R.id.mainViewPager)).perform(ViewActions.swipeLeft())
        SystemClock.sleep(waitingTimeAPI) //Wait Data Get
        onView(allOf(withId(R.id.moviesProgressBar), not(isDisplayed())))
        onView(withId(R.id.mainViewPager)).perform(ViewActions.swipeRight())

        SystemClock.sleep(waitingTime)
        onView(allOf(withId(R.id.moviesRV), isDisplayed())).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(allOf(withId(R.id.moviesRV), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))

        SystemClock.sleep(waitingTimeAPI) //Wait Data Get
        onView(withId(R.id.detailProgressBar)).check(matches(not(isDisplayed())))

        onView(withId(R.id.detailIMGBackdrop)).check(matches(isDisplayed()))
        onView(withId(R.id.detailIMGPoster)).check(matches(isDisplayed()))
        onView(withId(R.id.detailTVRating)).check(matches(isDisplayed()))
        onView(withId(R.id.detailTVTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.detailTVDate)).check(matches(isDisplayed()))
        onView(withId(R.id.detailTVGenre)).check(matches(isDisplayed()))
        onView(withId(R.id.detailTVOverview)).check(matches(isDisplayed()))
        onView(withId(R.id.detailTVLink)).check(matches(isDisplayed()))
    }
}