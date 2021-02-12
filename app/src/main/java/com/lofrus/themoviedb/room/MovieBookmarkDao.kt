package com.lofrus.themoviedb.room

import androidx.room.*

@Dao
interface MovieBookmarkDao {
    @Insert
    fun insert(movieBookmarkEntity: MovieBookmarkEntity)

    @Update
    fun update(movieBookmarkEntity: MovieBookmarkEntity)

    @Update
    fun delete(movieBookmarkEntity: MovieBookmarkEntity)



    @Query("SELECT * FROM MovieBookmarkEntity WHERE type=='0'")
    fun getAllLocalMovies(): List<MovieBookmarkEntity>

    @Query("SELECT * FROM MovieBookmarkEntity WHERE type=='1'")
    fun getAllLocalTVShow(): List<MovieBookmarkEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovieToLocal(listMovie: List<MovieBookmarkEntity>)

    @Query("SELECT * FROM MovieBookmarkEntity WHERE type=='0' AND bookmark=='1'")
    fun getAllBookmarkMovies(): List<MovieBookmarkEntity>

    @Query("SELECT * FROM MovieBookmarkEntity WHERE type=='1' AND bookmark=='1'")
    fun getAllBookmarkTVShow(): List<MovieBookmarkEntity>

    @Query("SELECT * FROM MovieBookmarkEntity WHERE id==:id AND bookmark=='1'")
    fun getBookmarkWithId(id: Int): List<MovieBookmarkEntity>

//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertBookmarkMovie(movieBookmarkEntity: MovieBookmarkEntity)
//
//    @Query("DELETE FROM MovieBookmarkEntity WHERE id==:id")
//    fun deleteBookmarkWithId(id: Int): Int


//
//    @Query("SELECT * FROM MovieBookmarkEntity")
//    fun getAllBookmark(): List<MovieBookmarkEntity>



}