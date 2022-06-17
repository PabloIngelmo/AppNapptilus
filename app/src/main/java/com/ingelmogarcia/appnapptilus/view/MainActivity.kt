package com.ingelmogarcia.appnapptilus.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ingelmogarcia.appnapptilus.OompaLoompaAdapter
import com.ingelmogarcia.appnapptilus.databinding.ActivityMainBinding
import com.ingelmogarcia.appnapptilus.model.OompaLoompaModel
import com.ingelmogarcia.appnapptilus.viewmodel.OompaLoompaViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val oompaLoompaViewModel : OompaLoompaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = GridLayoutManager(this,3)
        binding.recycler.layoutManager = layoutManager

        binding.recycler.adapter = OompaLoompaAdapter(listOf(
            OompaLoompaModel("https://s3.eu-central-1.amazonaws.com/napptilus/level-test/1.jpg","AAA"),
            OompaLoompaModel("https://s3.eu-central-1.amazonaws.com/napptilus/level-test/2.jpg","BBB"),
            OompaLoompaModel("https://s3.eu-central-1.amazonaws.com/napptilus/level-test/3.jpg","CCC"),
            OompaLoompaModel("https://s3.eu-central-1.amazonaws.com/napptilus/level-test/4.jpg","DDD"),
            OompaLoompaModel("https://s3.eu-central-1.amazonaws.com/napptilus/level-test/5.jpg","EEE"),
            OompaLoompaModel("https://s3.eu-central-1.amazonaws.com/napptilus/level-test/6.jpg","FFF"),
            OompaLoompaModel("https://s3.eu-central-1.amazonaws.com/napptilus/level-test/7.jpg","GGG"),
            OompaLoompaModel("https://s3.eu-central-1.amazonaws.com/napptilus/level-test/8.jpg","HHH"),
            OompaLoompaModel("https://s3.eu-central-1.amazonaws.com/napptilus/level-test/8.jpg","III"),
            OompaLoompaModel("https://s3.eu-central-1.amazonaws.com/napptilus/level-test/1.jpg","AAA"),
            OompaLoompaModel("https://s3.eu-central-1.amazonaws.com/napptilus/level-test/2.jpg","BBB"),
            OompaLoompaModel("https://s3.eu-central-1.amazonaws.com/napptilus/level-test/3.jpg","CCC"),
            OompaLoompaModel("https://s3.eu-central-1.amazonaws.com/napptilus/level-test/4.jpg","DDD"),
            OompaLoompaModel("https://s3.eu-central-1.amazonaws.com/napptilus/level-test/5.jpg","EEE"),
            OompaLoompaModel("https://s3.eu-central-1.amazonaws.com/napptilus/level-test/6.jpg","FFF"),
            OompaLoompaModel("https://s3.eu-central-1.amazonaws.com/napptilus/level-test/7.jpg","GGG"),
            OompaLoompaModel("https://s3.eu-central-1.amazonaws.com/napptilus/level-test/8.jpg","HHH"),
            OompaLoompaModel("https://s3.eu-central-1.amazonaws.com/napptilus/level-test/8.jpg","III")
        ))
        oompaLoompaViewModel.oompaLoompaModel.observe(this, Observer {

        })
    }
}