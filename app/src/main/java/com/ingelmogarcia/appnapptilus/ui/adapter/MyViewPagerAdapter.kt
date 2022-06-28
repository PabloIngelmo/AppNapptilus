package com.ingelmogarcia.appnapptilus.ui.adapter

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ingelmogarcia.appnapptilus.ui.view.fragment.ViewPagerFragmentFour
import com.ingelmogarcia.appnapptilus.ui.view.fragment.ViewPagerFragmentOne
import com.ingelmogarcia.appnapptilus.ui.view.fragment.ViewPagerFragmentThree
import com.ingelmogarcia.appnapptilus.ui.view.fragment.ViewPagerFragmentTwo


class MyViewPagerAdapter(manager: FragmentActivity): FragmentStateAdapter(manager) {

    val POSITION_NOT_FOUND = "Position Not Found"

    override fun getItemCount() = 4

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> { ViewPagerFragmentOne() }
            1 -> { ViewPagerFragmentTwo() }
            2 -> { ViewPagerFragmentThree() }
            3 -> { ViewPagerFragmentFour() }
            else -> { throw Resources.NotFoundException(POSITION_NOT_FOUND) }
        }
    }


}