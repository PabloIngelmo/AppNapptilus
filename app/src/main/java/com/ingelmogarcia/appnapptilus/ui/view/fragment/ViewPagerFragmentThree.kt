package com.ingelmogarcia.appnapptilus.ui.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.ingelmogarcia.appnapptilus.R
import com.ingelmogarcia.appnapptilus.ui.view.activity.OompaLoompaDetailActivity


class ViewPagerFragmentThree : Fragment() {

    lateinit var textViewQuota: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_view_pager_three, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textViewQuota = view.findViewById(R.id.textViewQuota)
        if(OompaLoompaDetailActivity.oompaLoompaDetail != null){
            textViewQuota.text = OompaLoompaDetailActivity.oompaLoompaDetail!!.quota
        }
    }
}