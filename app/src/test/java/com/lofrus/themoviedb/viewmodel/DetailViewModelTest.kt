package com.lofrus.themoviedb.viewmodel

import com.lofrus.themoviedb.utils.DataDummy
import junit.framework.TestCase
import org.junit.Before

class DetailViewModelTest : TestCase() {

    private lateinit var viewModel: DetailViewModel
    private val dataMovieDummy = DataDummy.generateDummyMovieDetail(0)

    @Before
    override fun setUp() {
        viewModel = DetailViewModel()
        viewModel.setMoviesDetailDummy(dataMovieDummy)
    }

    fun testSetMoviesDetailDummy() {
        viewModel.setMoviesDetailDummy(DataDummy.generateDummyMovieDetail(0))
    }

    fun testSetTVShowDetailDummy() {
        viewModel.setTVShowDetailDummy(DataDummy.generateDummyMovieDetail(1))
    }

    fun testGetMovieDetailDummy() {
        val detailMovie = viewModel.getMovieDetailDummy(2)
        assertNotNull(detailMovie)
        assertEquals(dataMovieDummy.id, detailMovie.id)
        assertEquals(dataMovieDummy.title, detailMovie.title)
        assertEquals(dataMovieDummy.date, detailMovie.date)
        assertEquals(dataMovieDummy.genre, detailMovie.genre)
        assertEquals(dataMovieDummy.rating, detailMovie.rating)
        assertEquals(dataMovieDummy.poster, detailMovie.poster)
        assertEquals(dataMovieDummy.backdrop, detailMovie.backdrop)
        assertEquals(dataMovieDummy.overview, detailMovie.overview)
        assertEquals(dataMovieDummy.link, detailMovie.link)
    }
}