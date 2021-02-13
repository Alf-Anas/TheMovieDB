package com.lofrus.themoviedb.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.lofrus.themoviedb.data.TheMovieDBRepository
import com.lofrus.themoviedb.model.DetailMovieEntity
import com.lofrus.themoviedb.utils.DataDummy
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest{

    private lateinit var viewModel: DetailViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var theMovieDBRepository: TheMovieDBRepository

    @Mock
    private lateinit var observer: Observer<DetailMovieEntity>

    @Mock
    private lateinit var observerError: Observer<String?>

    @Before
    fun setUp() {
        viewModel = DetailViewModel(theMovieDBRepository)
    }

    @Test
    fun testGetStatusError() {
        val dummyError = DataDummy.generateDummyError()
        val errorData = MutableLiveData<String?>()
        errorData.value = dummyError

        `when`(theMovieDBRepository.getStatusError()).thenReturn(errorData)
        val errorDB = viewModel.getStatusError().value
        verify(theMovieDBRepository).getStatusError()
        assertNotNull(errorDB)
        assertEquals("ERROR!", errorDB)

        viewModel.getStatusError().observeForever(observerError)
        verify(observerError).onChanged(dummyError)
    }

    @Test
    fun testSetMoviesDetail() {
        val detailViewModel: DetailViewModel = mock(DetailViewModel::class.java)
        detailViewModel.setMoviesDetail(0)
        verify(detailViewModel, times(1)).setMoviesDetail(0)
    }

    @Test
    fun testSetTVShowDetail() {
        val detailViewModel: DetailViewModel = mock(DetailViewModel::class.java)
        detailViewModel.setTVShowDetail(1)
        verify(detailViewModel, times(1)).setTVShowDetail(1)
    }

    @Test
    fun testGetMovieDetail() {
        val dummyMovieDetail = DataDummy.generateDummyMovieDetail(0)
        val movieDetail = MutableLiveData<DetailMovieEntity>()
        movieDetail.value = dummyMovieDetail

        `when`(theMovieDBRepository.getMovieDetail()).thenReturn(movieDetail)
        val movieDetailDB = viewModel.getMovieDetail().value
        verify(theMovieDBRepository).getMovieDetail()

        assertNotNull(movieDetailDB)
        assertEquals(dummyMovieDetail.id, movieDetailDB?.id)
        assertEquals(dummyMovieDetail.title, movieDetailDB?.title)
        assertEquals(dummyMovieDetail.date, movieDetailDB?.date)
        assertEquals(dummyMovieDetail.genre, movieDetailDB?.genre)
        assertEquals(dummyMovieDetail.rating, movieDetailDB?.rating)
        assertEquals(dummyMovieDetail.poster, movieDetailDB?.poster)
        assertEquals(dummyMovieDetail.backdrop, movieDetailDB?.backdrop)
        assertEquals(dummyMovieDetail.overview, movieDetailDB?.overview)
        assertEquals(dummyMovieDetail.link, movieDetailDB?.link)

        viewModel.getMovieDetail().observeForever(observer)
        verify(observer).onChanged(dummyMovieDetail)
    }

}