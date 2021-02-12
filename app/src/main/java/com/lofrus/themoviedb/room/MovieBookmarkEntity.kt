package com.lofrus.themoviedb.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class MovieBookmarkEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "type")
    var type: Int = 0,

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "date")
    var date: String = "",

    @ColumnInfo(name = "rating")
    var rating: Double = 0.0,

    @ColumnInfo(name = "poster")
    var poster: String = "",

    @ColumnInfo(name = "bookmark")
    var bookmark: Boolean = false
) : Parcelable