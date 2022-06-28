package com.ingelmogarcia.appnapptilus.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ingelmogarcia.appnapptilus.R
import com.ingelmogarcia.appnapptilus.databinding.FragmentViewPagerOneBinding
import com.ingelmogarcia.appnapptilus.databinding.FragmentViewPagerThreeBinding
import com.ingelmogarcia.appnapptilus.ui.view.activity.OompaLoompaDetailActivity


class ViewPagerFragmentThree : Fragment() {

    private lateinit var binding: FragmentViewPagerThreeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentViewPagerThreeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        if(OompaLoompaDetailActivity.oompaLoompaDetail != null){
            binding.textViewRandomString.text = OompaLoompaDetailActivity.oompaLoompaDetail!!.favorite.random_string
        }

        return binding.root
    }

}