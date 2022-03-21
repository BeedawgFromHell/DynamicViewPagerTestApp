package com.example.dynamicviewpagertestapp.pager

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity, private val elements: List<Int>): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = elements.size

    override fun createFragment(position: Int) = PageFragment.newInstance(elements[position])

}