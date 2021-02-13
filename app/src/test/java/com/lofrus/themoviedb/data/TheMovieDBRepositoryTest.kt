package com.lofrus.themoviedb.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.lofrus.themoviedb.model.DetailMovieEntity
import com.lofrus.themoviedb.model.MovieEntity
import com.lofrus.themoviedb.utils.AppExecutors
import com.lofrus.themoviedb.utils.DataDummy
import com.lofrus.themoviedb.utils.PagedListUtil
import com.lofrus.themoviedb.vo.Resource
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class TheMovieDBRepositoryTest {

    private val remote = mock(RemoteDataSource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val appExecutors = mock(AppExecutors::class.java)
    private val theMovieDBRepository = FakeTheMovieDBRepository(remote, local, appExecutors)

    private val dummyRemoteListMovies = DataDummy.generateRemoteDummyListMovies()
    private val dummyRemoteListTVShow = DataDummy.generateRemoteDummyListTVShow()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun testGetListMovies() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getListMovies()).thenReturn(dataSourceFactory)
        theMovieDBRepository.getListMovies()

        val listMovie = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyListMovies()))
        verify(local).getListMovies()
        assertNotNull(listMovie.data)
        assertEquals(dummyRemoteListMovies.size.toLong(), listMovie.data?.size?.toLong())
    }

    @Test
    fun testGetListTVShow() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getListTVShow()).thenReturn(dataSourceFactory)
        theMovieDBRepository.getListTVShow()

        val listMovie = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyListTVShow()))
        verify(local).getListTVShow()
        assertNotNull(listMovie.data)
        assertEquals(dummyRemoteListTVShow.size.toLong(), listMovie.data?.size?.toLong())
    }

    @Test
    fun testGetListMoviesBookmark() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getListMoviesBookmark()).thenReturn(dataSourceFactory)
        theMovieDBRepository.getListMoviesBookmark()

        val listMovie = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyListMovies()))
        verify(local).getListMoviesBookmark()
        assertNotNull(listMovie)
        assertEquals(dummyRemoteListMovies.size.toLong(), listMovie.data?.size?.toLong())
    }

    @Test
    fun testGetListTVShowBookmark() {
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(local.getListTVShowBookmark()).thenReturn(dataSourceFactory)
        theMovieDBRepository.getListTVShowBookmark()

        val listMovie = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDummyListTVShow()))
        verify(local).getListTVShowBookmark()
        assertNotNull(listMovie.data)
        assertEquals(dummyRemoteListTVShow.size.toLong(), listMovie.data?.size?.toLong())
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