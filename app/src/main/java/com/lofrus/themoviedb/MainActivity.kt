package com.lofrus.themoviedb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.lofrus.themoviedb.databinding.ActivityMainBinding
import com.lofrus.themoviedb.fragment.MainPagerAdapter


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainPagerAdapter = MainPagerAdapter(this)
        binding.mainViewPager.adapter = mainPagerAdapter
        TabLayoutMediator(binding.mainTabLayout, binding.mainViewPager) { tab: TabLayout.Tab, position: Int ->
            tab.text = resources.getString(intArrayOf(R.string.nav_movies, R.string.nav_tv_show)[position])
        }.attach()
    }

}