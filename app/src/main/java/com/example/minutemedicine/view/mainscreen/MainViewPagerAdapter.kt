@file:Suppress("DEPRECATION")

package com.example.minutemedicine.view.mainscreen

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.minutemedicine.view.add.AddAlarmFragment
import com.example.minutemedicine.view.browse.BrowseFragment
import com.example.minutemedicine.view.clock.ClockFragment
import com.example.minutemedicine.view.home.HomeFragment
import com.example.minutemedicine.view.profile.ProfileFragment

class MainViewPagerAdapter(fragmentManager: FragmentManager) :
    FragmentStatePagerAdapter(fragmentManager) {

    private val fragments = arrayOf(
        HomeFragment.newInstance(),
        BrowseFragment.newInstance(),
        AddAlarmFragment.newInstance(),
        ClockFragment.newInstance(),
        ProfileFragment.newInstance()
    )

    override fun getCount() = fragments.size
    override fun getItem(position: Int) = fragments[position]
}