package com.ingelmogarcia.appnapptilus.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.ingelmogarcia.appnapptilus.databinding.ActivityMainBinding
import com.ingelmogarcia.appnapptilus.viewmodel.OompaLoompaViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val oompaLoompaViewModel : OompaLoompaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.oompaLoompa.setOompaLoompa("https://s3.eu-central-1.amazonaws.com/napptilus/level-test/2.jpg","AAA")

        oompaLoompaViewModel.oompaLoompaModel.observe(this, Observer {

        })
    }
}