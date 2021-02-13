package com.lofrus.themoviedb.room

import androidx.paging.DataSource
import androidx.room.*
import com.lofrus.themoviedb.model.MovieEntity

@Dao
interface MovieBookmarkDao {
    @Insert
    fun insert(movieBookmarkEntity: MovieBookmarkEntity)

    @Update
    fun update(movieBookmarkEntity: MovieBookmarkEntity)

    @Update
    fun delete(movieBookmarkEntity: MovieBookmarkEntity)

    @Query("SELECT * FROM MovieBookmarkEntity WHERE type=='0'")
    fun getAllLocalMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM MovieBookmarkEntity WHERE type=='1'")
    fun getAllLocalTVShow(): DataSource.Factory<Int, MovieEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovieToLocal(listMovie: List<MovieBookmarkEntity>)

    @Query("SELECT * FROM MovieBookmarkEntity WHERE type=='0' AND bookmark=='1'")
    fun getAllBookmarkMovies(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM MovieBookmarkEntity WHERE type=='1' AND bookmark=='1'")
    fun getAllBookmarkTVShow(): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT * FROM MovieBookmarkEntity WHERE id==:id AND bookmark=='1'")
    fun getBookmarkWithId(id: Int): List<MovieBookmarkEntity>

}