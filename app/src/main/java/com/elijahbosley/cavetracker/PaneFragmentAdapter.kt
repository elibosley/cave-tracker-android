package com.elijahbosley.cavetracker

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class PaneFragmentAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val fragments = arrayOf<Fragment>(CaveFragment.newInstance(), StatisticsFragment.newInstance())

    val titles = ArrayList<String>()
    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size
}
