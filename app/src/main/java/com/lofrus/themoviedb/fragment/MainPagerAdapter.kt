package com.lofrus.themoviedb.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.lofrus.themoviedb.fragment.MoviesFragment.Companion.MOVIES_
import com.lofrus.themoviedb.fragment.MoviesFragment.Companion.MOVIES_BOOKMARK_
import com.lofrus.themoviedb.fragment.MoviesFragment.Companion.TV_SHOW_
import com.lofrus.themoviedb.fragment.MoviesFragment.Companion.TV_SHOW_BOOKMARK_

class MainPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {

    private var listFragment = listOf(MoviesFragment.newInstance(MOVIES_), MoviesFragment.newInstance(TV_SHOW_))

    fun bookmarkListFragment() {
        this.listFragment = listOf(MoviesFragment.newInstance(MOVIES_BOOKMARK_), MoviesFragment.newInstance(TV_SHOW_BOOKMARK_))
    }

    override fun getItemCount(): Int = listFragment.size

    override fun createFragment(position: Int): Fragment {
        return listFragment[position]
    }
}