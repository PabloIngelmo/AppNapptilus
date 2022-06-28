package com.ingelmogarcia.appnapptilus.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ingelmogarcia.appnapptilus.databinding.FragmentViewPagerTwoBinding
import com.ingelmogarcia.appnapptilus.ui.view.activity.OompaLoompaDetailActivity


class ViewPagerFragmentTwo : Fragment() {

    private lateinit var binding: FragmentViewPagerTwoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentViewPagerTwoBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        if(OompaLoompaDetailActivity.oompaLoompaDetail != null){
            binding.textViewSong.text = OompaLoompaDetailActivity.oompaLoompaDetail!!.favorite.song
        }

        return binding.root
    }

}