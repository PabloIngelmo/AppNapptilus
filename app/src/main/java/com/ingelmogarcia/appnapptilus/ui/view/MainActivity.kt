package com.ingelmogarcia.appnapptilus.ui.view

import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.ingelmogarcia.appnapptilus.OompaLoompaListAdapter
import com.ingelmogarcia.appnapptilus.R
import com.ingelmogarcia.appnapptilus.data.model.OompaLoompaModel
import com.ingelmogarcia.appnapptilus.databinding.ActivityMainBinding
import com.ingelmogarcia.appnapptilus.ui.components.FiltersDialog
import com.ingelmogarcia.appnapptilus.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel : MainViewModel by viewModels()
    val mutableList : MutableList<OompaLoompaModel> = mutableListOf()
    private lateinit var searchView: SearchView
    private var genderFilter = "-"
    private var professionFilter = "-"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("Candidates")
        binding.buttonPreviousPage.text = "<"
        binding.buttonNextPage.text = ">"

        mainViewModel.onCreate()
        binding.oompaLoompaListRecycler.layoutManager = GridLayoutManager(this,5)


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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        searchView = menu.findItem(R.id.appBarSearchOption).actionView as SearchView
        searchView.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS
        searchView.setOnQueryTextListener(this)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if(id == R.id.appBarFilterOption){
            FiltersDialog(
                onSubmitClickListener = {
                    genderFilter = it.optionSp1
                    professionFilter = it.optionSp2
                    Toast.makeText(this, "GENERO: " + genderFilter + "PROFESSION: " + professionFilter, Toast.LENGTH_SHORT).show()
                }
            ).show(supportFragmentManager,"dialog")
        }

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        val a = mutableList.filter { it.title.toLowerCase().contains(query.toString().toLowerCase())}
        binding.oompaLoompaListRecycler.adapter = OompaLoompaListAdapter(a)
        dismissKeyboard()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        val a = mutableList.filter { it.title.toLowerCase().contains(newText.toString().toLowerCase())}
        binding.oompaLoompaListRecycler.adapter = OompaLoompaListAdapter(a)
        return true
    }

    private fun dismissKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }

}