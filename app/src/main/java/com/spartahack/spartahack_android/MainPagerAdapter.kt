package com.spartahack.spartahack_android

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

/*
 * All of this code is copied from the link below. It handles the fragments and the view
 * pager.
 * https://pspdfkit.com/blog/2019/using-the-bottom-navigation-view-in-android/
 */

class MainPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val screens = arrayListOf<MainScreen>()

    fun setItems(screens: List<MainScreen>) {
        this.screens.apply {
            clear()
            addAll(screens)
            notifyDataSetChanged()
        }
    }

    fun getItems(): List<MainScreen> {
        return screens
    }

    override fun getItem(position: Int): Fragment {
        return screens[position].fragment
    }

    override fun getCount(): Int {
        return screens.size
    }
}

