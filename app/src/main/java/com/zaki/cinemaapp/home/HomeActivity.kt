package com.zaki.cinemaapp.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.zaki.cinemaapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private var _homeBinding: ActivityHomeBinding? = null
    private val homeBinding get() = _homeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _homeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(homeBinding?.root)

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)
        homeBinding?.viewPager?.adapter = sectionPagerAdapter
        homeBinding?.tabs?.setupWithViewPager(homeBinding?.viewPager)

        supportActionBar?.elevation = 0f
    }

    override fun onDestroy() {
        super.onDestroy()
        _homeBinding = null
    }
}