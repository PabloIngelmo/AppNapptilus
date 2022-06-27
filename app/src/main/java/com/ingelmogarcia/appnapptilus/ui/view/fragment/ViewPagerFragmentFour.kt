package com.ingelmogarcia.appnapptilus.ui.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ingelmogarcia.appnapptilus.R
import com.ingelmogarcia.appnapptilus.databinding.FragmentViewPagerFourBinding
import com.ingelmogarcia.appnapptilus.databinding.FragmentViewPagerThreeBinding
import com.ingelmogarcia.appnapptilus.ui.view.activity.OompaLoompaDetailActivity


class ViewPagerFragmentFour : Fragment() {

    private lateinit var binding: FragmentViewPagerFourBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentViewPagerFourBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        if(OompaLoompaDetailActivity.oompaLoompaDetail != null){
            binding.textViewQuota.text = OompaLoompaDetailActivity.oompaLoompaDetail!!.quota
        }

        return binding.root
    }

}