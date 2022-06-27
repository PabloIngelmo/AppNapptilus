package com.ingelmogarcia.appnapptilus.ui

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ingelmogarcia.appnapptilus.ui.view.fragment.ViewPagerFragmentOne
import com.ingelmogarcia.appnapptilus.ui.view.fragment.ViewPagerFragmentThree
import com.ingelmogarcia.appnapptilus.ui.view.fragment.ViewPagerFragmentTwo


class MyViewPagerAdapter(manager: FragmentActivity): FragmentStateAdapter(manager) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> { ViewPagerFragmentOne() }
            1 -> { ViewPagerFragmentTwo() }
            2 -> { ViewPagerFragmentThree() }
            else -> { throw Resources.NotFoundException("Position Not Found") }
        }
    }


}