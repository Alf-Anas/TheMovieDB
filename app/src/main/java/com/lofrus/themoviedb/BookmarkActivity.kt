package com.lofrus.themoviedb

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lofrus.themoviedb.databinding.ActivityBookmarkBinding
import com.lofrus.themoviedb.fragment.MainPagerAdapter

class BookmarkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookmarkBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBookmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val mainPagerAdapter = MainPagerAdapter(this)
        mainPagerAdapter.bookmarkListFragment()
        binding.bookmarkViewPager.adapter = mainPagerAdapter
        TabLayoutMediator(binding.bookmarkTabLayout, binding.bookmarkViewPager) { tab: TabLayout.Tab, position: Int ->
            tab.text = resources.getString(intArrayOf(R.string.nav_movies, R.string.nav_tv_show)[position])
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}