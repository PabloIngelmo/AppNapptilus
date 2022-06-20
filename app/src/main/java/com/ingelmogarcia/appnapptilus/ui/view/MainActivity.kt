package com.ingelmogarcia.appnapptilus.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.ingelmogarcia.appnapptilus.OompaLoompaListAdapter
import com.ingelmogarcia.appnapptilus.data.model.OompaLoompaModel
import com.ingelmogarcia.appnapptilus.databinding.ActivityMainBinding
import com.ingelmogarcia.appnapptilus.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel : MainViewModel by viewModels()
    val mutableList : MutableList<OompaLoompaModel> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonPreviousPage.text = "<"
        binding.buttonNextPage.text = ">"

        mainViewModel.onCreate()
        val layoutManager = GridLayoutManager(this,5)
        binding.oompaLoompaListRecycler.layoutManager = layoutManager


        mainViewModel.dataPageModel.observe(this, Observer {
            mutableList.clear()
            for (i in it.results){
                mutableList.add(OompaLoompaModel(i.urlImage,i.first_name))
            }
            binding.oompaLoompaListRecycler.adapter = OompaLoompaListAdapter(mutableList)
            binding.textviewNumPage.text = it.current.toString() + "/" + it.total
            if(it.current > 1){ binding.buttonPreviousPage.isEnabled = true }
            else{ binding.buttonPreviousPage.isEnabled = false }
            if(it.current < 20){ binding.buttonNextPage.isEnabled = true }
            else{ binding.buttonNextPage.isEnabled = false }
        })

        binding.buttonNextPage.setOnClickListener {
            mainViewModel.downloadData(1)
        }

        binding.buttonPreviousPage.setOnClickListener {
            mainViewModel.downloadData(-1)
        }

    }
}