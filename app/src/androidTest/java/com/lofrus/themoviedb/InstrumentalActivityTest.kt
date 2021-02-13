package com.lofrus.themoviedb

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.lofrus.themoviedb.utils.EspressoIdlingResource
import org.hamcrest.core.AllOf.allOf
import org.hamcrest.core.IsNot
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class InstrumentalActivityTest {

    private lateinit var instrumentalContext: Context

    @Before
    fun setup() {
        instrumentalContext = InstrumentationRegistry.getInstrumentation().targetContext
        ActivityScenario.launch(SplashScreenActivity::class.java)
    }

    @Test
    fun testOnCreate() {
        onView(withId(R.id.splTVVersion)).check(matches(isDisplayed()))
        onView(withId(R.id.splTVVersion)).check(matches(withText("© Submission 1 Dicoding - Android Jetpack Pro")))

        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
        testMainReady()
    }

    private fun testMainReady() {
        onView(withId(R.id.menuBookmarks)).check(matches(isDisplayed()))
        onView(withId(R.id.mainTabLayout)).check(matches(isDisplayed()))
        onView(withId(R.id.mainViewPager)).check(matches(isDisplayed()))
        testLoadMovies()
    }

    private fun testLoadMovies() {
        onView(allOf(withId(R.id.moviesProgressBar), IsNot.not(isDisplayed())))
        onView(allOf(withId(R.id.moviesRV), isDisplayed())).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(2))
        onView(allOf(withId(R.id.moviesRV), isDisplayed())).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click())
        )
        testMoviesDetail()
    }

    private fun testMoviesDetail() {
        onView(withId(R.id.detailProgressBar)).check(matches(IsNot.not(isDisplayed())))

        onView(withId(R.id.detailIMGBackdrop)).check(matches(isDisplayed()))
        onView(withId(R.id.detailIMGPoster)).check(matches(isDisplayed()))
        onView(withId(R.id.detailTVRating)).check(matches(isDisplayed()))
        onView(withId(R.id.detailTVTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.detailTVDate)).check(matches(isDisplayed()))
        onView(withId(R.id.detailTVGenre)).check(matches(isDisplayed()))
        onView(withId(R.id.detailTVOverview)).check(matches(isDisplayed()))
        onView(withId(R.id.detailTVLink)).check(matches(isDisplayed()))

        onView(withId(R.id.detailFABBookmark)).perform(click())

        onView(isRoot()).perform(pressBack())
        testLoadTVShow()
    }

    private fun testLoadTVShow() {
        onView(withId(R.id.mainViewPager)).perform(swipeLeft())
        onView(withId(R.id.mainViewPager)).perform(swipeLeft())

        onView(allOf(withId(R.id.moviesProgressBar), IsNot.not(isDisplayed())))
        onView(allOf(withId(R.id.moviesRV), isDisplayed())).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(4))
        onView(allOf(withId(R.id.moviesRV), isDisplayed())).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(4, click())
        )
        testTVShowDetail()
    }

    private fun testTVShowDetail() {
        onView(withId(R.id.detailProgressBar)).check(matches(IsNot.not(isDisplayed())))

        onView(withId(R.id.detailIMGBackdrop)).check(matches(isDisplayed()))
        onView(withId(R.id.detailIMGPoster)).check(matches(isDisplayed()))
        onView(withId(R.id.detailTVRating)).check(matches(isDisplayed()))
        onView(withId(R.id.detailTVTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.detailTVDate)).check(matches(isDisplayed()))
        onView(withId(R.id.detailTVGenre)).check(matches(isDisplayed()))
        onView(withId(R.id.detailTVOverview)).check(matches(isDisplayed()))
        onView(withId(R.id.detailTVLink)).check(matches(isDisplayed()))

        onView(withId(R.id.detailFABBookmark)).perform(click())

        onView(isRoot()).perform(pressBack())
        testBookmarkReady()
    }

    private fun testBookmarkReady() {
        onView(withId(R.id.menuBookmarks)).perform(click())
        onView(withId(R.id.bookmarkTabLayout)).check(matches(isDisplayed()))
        onView(withId(R.id.bookmarkViewPager)).check(matches(isDisplayed()))
        testLoadMoviesBookmark()
    }

    private fun testLoadMoviesBookmark() {
        onView(allOf(withId(R.id.moviesProgressBar), IsNot.not(isDisplayed())))
        onView(allOf(withId(R.id.moviesRV), isDisplayed())).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(allOf(withId(R.id.moviesRV), isDisplayed())).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )

        onView(withId(R.id.detailProgressBar)).check(matches(IsNot.not(isDisplayed())))
        onView(withId(R.id.detailFABBookmark)).perform(click())
        onView(isRoot()).perform(pressBack())
        testLoadTVShowBookmark()
    }

    private fun testLoadTVShowBookmark() {
        onView(withId(R.id.bookmarkViewPager)).perform(swipeLeft())
        onView(withId(R.id.bookmarkViewPager)).perform(swipeLeft())

        onView(allOf(withId(R.id.moviesProgressBar), IsNot.not(isDisplayed())))
        onView(allOf(withId(R.id.moviesRV), isDisplayed())).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(allOf(withId(R.id.moviesRV), isDisplayed())).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )

        onView(withId(R.id.detailProgressBar)).check(matches(IsNot.not(isDisplayed())))
        onView(withId(R.id.detailFABBookmark)).perform(click())
        onView(isRoot()).perform(pressBack())

        onView(isRoot()).perform(pressBack())
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }
}