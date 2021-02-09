package com.lofrus.themoviedb.fragment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.lofrus.themoviedb.data.TheMovieDBRepository
import com.lofrus.themoviedb.model.MovieEntity
import com.lofrus.themoviedb.utils.DataDummy
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesViewModelTest {

    private lateinit var viewModel: MoviesViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var theMovieDBRepository: TheMovieDBRepository

    @Mock
    private lateinit var observer: Observer<ArrayList<MovieEntity>>

    @Mock
    private lateinit var observerError: Observer<String?>

    @Before
     fun setUp() {
        viewModel = MoviesViewModel(theMovieDBRepository)
    }

    @Test
    fun testSetListMovies() {
        val moviesViewModel: MoviesViewModel = mock(MoviesViewModel::class.java)
        moviesViewModel.setListMovies()
        verify(moviesViewModel, times(1)).setListMovies()
    }

    @Test
    fun testSetListTVShow() {
        val moviesViewModel: MoviesViewModel = mock(MoviesViewModel::class.java)
        moviesViewModel.setListTVShow()
        verify(moviesViewModel, times(1)).setListTVShow()
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
    fun testGetListMovie() {
        val dummyListMovie = DataDummy.generateDummyListMovies()
        val listMovie = MutableLiveData<ArrayList<MovieEntity>>()
        listMovie.value = dummyListMovie

        `when`(theMovieDBRepository.getListMovie()).thenReturn(listMovie)
        val listMovieDB = viewModel.getListMovie().value
        verify(theMovieDBRepository).getListMovie()
        assertNotNull(listMovieDB)
        assertEquals(7, listMovieDB?.size)

        viewModel.getListMovie().observeForever(observer)
        verify(observer).onChanged(dummyListMovie)
    }

    @Test
    fun testGetListMoviesDummy() {
        `when`(theMovieDBRepository.getListMoviesDummy()).thenReturn(DataDummy.generateDummyListMovies() as ArrayList<MovieEntity>)
        val listMovie = viewModel.getListMoviesDummy()
        verify(theMovieDBRepository).getListMoviesDummy()
        Assert.assertNotNull(listMovie)
        Assert.assertEquals(7, listMovie.size)
    }

    @Test
    fun testGetListTVShowDummy() {
        `when`(theMovieDBRepository.getListTVShowDummy()).thenReturn(DataDummy.generateDummyListTVShow() as ArrayList<MovieEntity>)
        val listTVShow = theMovieDBRepository.getListTVShowDummy()
        verify(theMovieDBRepository).getListTVShowDummy()
        assertNotNull(listTVShow)
        assertEquals(6, listTVShow.size)
    }


}
