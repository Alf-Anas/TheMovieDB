package com.lofrus.themoviedb.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.lofrus.themoviedb.model.DetailMovieEntity
import com.lofrus.themoviedb.model.MovieEntity
import com.lofrus.themoviedb.utils.DataDummy
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class TheMovieDBRepositoryTest {

    private val remote = mock(RemoteDataSource::class.java)
    private val theMovieDBRepository = FakeTheMovieDBRepository(remote)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun testSetListMovies() {
        val theMovieDBRepositoryLocal: FakeTheMovieDBRepository = mock(FakeTheMovieDBRepository::class.java)
        theMovieDBRepositoryLocal.setListMovies()
        verify(theMovieDBRepositoryLocal, times(1)).setListMovies()
    }

    @Test
    fun testSetListTVShow() {
        val theMovieDBRepositoryLocal: FakeTheMovieDBRepository = mock(FakeTheMovieDBRepository::class.java)
        theMovieDBRepositoryLocal.setListTVShow()
        verify(theMovieDBRepositoryLocal, times(1)).setListTVShow()
    }

    @Test
    fun testGetListMovie() {
        val listMovie = DataDummy.generateRemoteDummyListMovies()
        val listMovieEntity = MutableLiveData<ArrayList<MovieEntity>>()
        listMovieEntity.value = listMovie

        `when`(remote.listMovie).thenReturn(listMovieEntity)
        val listMovieDB = theMovieDBRepository.getListMovie().value
        verify(remote).listMovie

        assertNotNull(listMovieDB)
        assertEquals(7, listMovieDB?.size)
    }

    @Test
    fun testGetStatusError() {
        val error = DataDummy.generateRemoteDummyError()
        val errorStr = MutableLiveData<String?>()
        errorStr.value = error

        `when`(remote.statusError).thenReturn(errorStr)
        val errorDB = theMovieDBRepository.getStatusError().value
        verify(remote).statusError

        assertNotNull(errorDB)
        assertEquals("ERROR!", errorDB)
    }

    @Test
    fun testSetMoviesDetail() {
        val theMovieDBRepositoryLocal: FakeTheMovieDBRepository = mock(FakeTheMovieDBRepository::class.java)
        theMovieDBRepositoryLocal.setMoviesDetail(0)
        verify(theMovieDBRepositoryLocal, times(1)).setMoviesDetail(0)
    }

    @Test
    fun testSetTVShowDetail() {
        val theMovieDBRepositoryLocal: FakeTheMovieDBRepository = mock(FakeTheMovieDBRepository::class.java)
        theMovieDBRepositoryLocal.setTVShowDetail(1)
        verify(theMovieDBRepositoryLocal, times(1)).setTVShowDetail(1)
    }

    @Test
    fun testGetMovieDetail() {
        val dummyMovieDetail = DataDummy.generateRemoteDummyMovieDetail(0)
        val movieDetail = MutableLiveData<DetailMovieEntity>()
        movieDetail.value = dummyMovieDetail

        `when`(remote.detailMovie).thenReturn(movieDetail)
        val movieDetailDB = theMovieDBRepository.getMovieDetail().value
        verify(remote).detailMovie

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
    }

}