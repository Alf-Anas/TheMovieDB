package com.lofrus.themoviedb.fragment

import junit.framework.TestCase
import org.junit.Assert
import org.junit.Before

class MoviesViewModelTest : TestCase() {

    private lateinit var viewModel: MoviesViewModel

    @Before
    override fun setUp() {
        viewModel = MoviesViewModel()
    }

    fun testGetListMoviesDummy() {
        val listMovie = viewModel.getListMoviesDummy()
        Assert.assertNotNull(listMovie)
        Assert.assertEquals(7, listMovie.size)
    }
    fun testGetListTVShowDummy() {
        val listTVShow = viewModel.getListTVShowDummy()
        Assert.assertNotNull(listTVShow)
        Assert.assertEquals(6, listTVShow.size)
    }

}