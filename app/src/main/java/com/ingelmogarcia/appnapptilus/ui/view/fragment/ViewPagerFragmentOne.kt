package com.ingelmogarcia.appnapptilus.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ingelmogarcia.appnapptilus.R
import com.ingelmogarcia.appnapptilus.databinding.ActivityMainBinding
import com.ingelmogarcia.appnapptilus.databinding.FragmentViewPagerOneBinding
import com.ingelmogarcia.appnapptilus.ui.view.activity.OompaLoompaDetailActivity


class ViewPagerFragmentOne : Fragment() {

    private lateinit var binding: FragmentViewPagerOneBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentViewPagerOneBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val oompaLoompaDetail = OompaLoompaDetailActivity.oompaLoompaDetail
        if(oompaLoompaDetail != null){
            with(binding){
                textViewDescription.text = oompaLoompaDetail.description
                textViewCountry.text = oompaLoompaDetail.country
                textViewAge.text = oompaLoompaDetail.age.toString()
                textViewProfession.text = oompaLoompaDetail.profession
                textViewHeight.text = oompaLoompaDetail.height.toString()
                textViewFood.text = oompaLoompaDetail.favorite.food
                textViewColor.text = oompaLoompaDetail.favorite.color
                textViewEmail.text = oompaLoompaDetail.email
            }
        }

        return binding.root
    }

}