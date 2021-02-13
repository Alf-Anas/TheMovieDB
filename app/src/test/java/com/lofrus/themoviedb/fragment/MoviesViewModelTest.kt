package com.lofrus.themoviedb.fragment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.lofrus.themoviedb.data.TheMovieDBRepository
import com.lofrus.themoviedb.model.MovieEntity
import com.lofrus.themoviedb.vo.Resource
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MoviesViewModelTest {

    private lateinit var viewModel: MoviesViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var theMovieDBRepository: TheMovieDBRepository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<MovieEntity>>>

    @Mock
    private lateinit var observerBookmark: Observer<PagedList<MovieEntity>>

    @Mock
    private lateinit var pagedList: PagedList<MovieEntity>

    @Before
    fun setUp() {
        viewModel = MoviesViewModel(theMovieDBRepository)
    }

    @Test
    fun testGetListMovies() {
        val dummyListMovies = Resource.success(pagedList)
        `when`(dummyListMovies.data?.size).thenReturn(7)
        val listMovie = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        listMovie.value = dummyListMovies

        `when`(theMovieDBRepository.getListMovies()).thenReturn(listMovie)
        val listMovieDB = viewModel.getListMovies().value?.data
        verify(theMovieDBRepository).getListMovies()
        assertNotNull(listMovieDB)
        assertEquals(7, listMovieDB?.size)

        viewModel.getListMovies().observeForever(observer)
        verify(observer).onChanged(dummyListMovies)
    }

    @Test
    fun testGetListTVShow() {
        val dummyListTVShow = Resource.success(pagedList)
        `when`(dummyListTVShow.data?.size).thenReturn(6)
        val listTVShow = MutableLiveData<Resource<PagedList<MovieEntity>>>()
        listTVShow.value = dummyListTVShow

        `when`(theMovieDBRepository.getListTVShow()).thenReturn(listTVShow)
        val listTVShowDB = viewModel.getListTVShow().value?.data
        verify(theMovieDBRepository).getListTVShow()
        assertNotNull(listTVShowDB)
        assertEquals(6, listTVShowDB?.size)

        viewModel.getListTVShow().observeForever(observer)
        verify(observer).onChanged(dummyListTVShow)
    }

    @Test
    fun testGetListMoviesBookmark() {
        val dummyListMoviesBookmark = pagedList
        `when`(dummyListMoviesBookmark.size).thenReturn(2)
        val listMovieBookmark = MutableLiveData<PagedList<MovieEntity>>()
        listMovieBookmark.value = dummyListMoviesBookmark

        `when`(theMovieDBRepository.getListMoviesBookmark()).thenReturn(listMovieBookmark)
        val listMovieBookmarkDB = viewModel.getListMoviesBookmark().value
        verify(theMovieDBRepository).getListMoviesBookmark()
        assertNotNull(listMovieBookmarkDB)
        assertEquals(2, listMovieBookmarkDB?.size)

        viewModel.getListMoviesBookmark().observeForever(observerBookmark)
        verify(observerBookmark).onChanged(dummyListMoviesBookmark)
    }

    @Test
    fun testGetListTVShowBookmark() {
        val dummyListTVShowBookmark = pagedList
        `when`(dummyListTVShowBookmark.size).thenReturn(3)
        val listTVShowBookmark = MutableLiveData<PagedList<MovieEntity>>()
        listTVShowBookmark.value = dummyListTVShowBookmark

        `when`(theMovieDBRepository.getListTVShowBookmark()).thenReturn(listTVShowBookmark)
        val listTVShowBookmarkDB = viewModel.getListTVShowBookmark().value
        verify(theMovieDBRepository).getListTVShowBookmark()
        assertNotNull(listTVShowBookmarkDB)
        assertEquals(3, listTVShowBookmarkDB?.size)

        viewModel.getListTVShowBookmark().observeForever(observerBookmark)
        verify(observerBookmark).onChanged(dummyListTVShowBookmark)
    }

}
