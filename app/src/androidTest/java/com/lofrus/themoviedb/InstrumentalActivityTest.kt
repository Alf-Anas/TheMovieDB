package com.lofrus.themoviedb

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.lofrus.themoviedb.utils.EspressoIdlingResource
import org.hamcrest.core.AllOf.allOf
import org.hamcrest.core.IsNot.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class InstrumentalActivityTest {

    private lateinit var instrumentalContext: Context

    @Before
    fun setup(){
        instrumentalContext = InstrumentationRegistry.getInstrumentation().targetContext
        ActivityScenario.launch(SplashScreenActivity::class.java)
    }

    @Test
    fun testOnCreate() {
        onView(withId(R.id.splTVVersion)).check(matches(isDisplayed()))
        onView(withId(R.id.splTVVersion)).check(matches(withText("© Submission 1 Dicoding - Android Jetpack Pro")))

        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())

        onView(withId(R.id.mainTabLayout)).check(matches(isDisplayed()))
        onView(withId(R.id.mainViewPager)).check(matches(isDisplayed()))

        onView(allOf(withId(R.id.moviesProgressBar), not(isDisplayed())))
        onView(allOf(withId(R.id.moviesRV), isDisplayed())).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        onView(allOf(withId(R.id.moviesRV), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, click()))

        onView(withId(R.id.detailProgressBar)).check(matches(not(isDisplayed())))

        onView(withId(R.id.detailIMGBackdrop)).check(matches(isDisplayed()))
        onView(withId(R.id.detailIMGPoster)).check(matches(isDisplayed()))
        onView(withId(R.id.detailTVRating)).check(matches(isDisplayed()))
        onView(withId(R.id.detailTVTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.detailTVDate)).check(matches(isDisplayed()))
        onView(withId(R.id.detailTVGenre)).check(matches(isDisplayed()))
        onView(withId(R.id.detailTVOverview)).check(matches(isDisplayed()))
        onView(withId(R.id.detailTVLink)).check(matches(isDisplayed()))

        onView(isRoot()).perform(ViewActions.pressBack())

        onView(withId(R.id.mainViewPager)).perform(ViewActions.swipeLeft())
        onView(withId(R.id.mainViewPager)).perform(ViewActions.swipeLeft())

        onView(allOf(withId(R.id.moviesProgressBar), not(isDisplayed())))
        onView(allOf(withId(R.id.moviesRV), isDisplayed())).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(allOf(withId(R.id.moviesRV), isDisplayed())).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))

        onView(withId(R.id.detailProgressBar)).check(matches(not(isDisplayed())))

        onView(withId(R.id.detailIMGBackdrop)).check(matches(isDisplayed()))
        onView(withId(R.id.detailIMGPoster)).check(matches(isDisplayed()))
        onView(withId(R.id.detailTVRating)).check(matches(isDisplayed()))
        onView(withId(R.id.detailTVTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.detailTVDate)).check(matches(isDisplayed()))
        onView(withId(R.id.detailTVGenre)).check(matches(isDisplayed()))
        onView(withId(R.id.detailTVOverview)).check(matches(isDisplayed()))
        onView(withId(R.id.detailTVLink)).check(matches(isDisplayed()))

        onView(isRoot()).perform(ViewActions.pressBack())
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }
}