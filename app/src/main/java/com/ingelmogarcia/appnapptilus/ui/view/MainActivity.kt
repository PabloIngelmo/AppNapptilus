package com.ingelmogarcia.appnapptilus.ui.view

import android.app.ProgressDialog
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.ingelmogarcia.appnapptilus.OompaLoompaListAdapter
import com.ingelmogarcia.appnapptilus.R
import com.ingelmogarcia.appnapptilus.data.model.DataPageModel
import com.ingelmogarcia.appnapptilus.data.model.OompaLoompaModel
import com.ingelmogarcia.appnapptilus.databinding.ActivityMainBinding
import com.ingelmogarcia.appnapptilus.ui.components.FiltersDialog
import com.ingelmogarcia.appnapptilus.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel : MainViewModel by viewModels()
    val mutableList : MutableList<OompaLoompaModel> = mutableListOf()
    private lateinit var dataPage: DataPageModel
    private lateinit var searchView: SearchView
    private lateinit var progressDialog: ProgressDialog
    private var genderFilter = "-"
    private var professionFilter = "-"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle("Candidates")
        binding.buttonPreviousPage.text = "<"
        binding.buttonNextPage.text = ">"
        binding.buttonFirstPage.text = "<<"
        binding.buttonLastPage.text = ">>"

        mainViewModel.onCreate()
        binding.oompaLoompaListRecycler.layoutManager = GridLayoutManager(this,5)

        mainViewModel.isLoading.observe(this, Observer {
            if(it){
                enabledProgressDialog()
            }else{
                disabledProgressDialog()
            }
        })

        mainViewModel.dataPageModel.observe(this, Observer {dataPage ->
            this.dataPage = dataPage
            mutableList.clear()
            for (i in dataPage.results){
                mutableList.add(OompaLoompaModel(i.urlImage,i.first_name,i.gender, i.profession))
            }
            binding.oompaLoompaListRecycler.adapter = OompaLoompaListAdapter(mutableList)
            binding.textviewNumPage.text = dataPage.current.toString() + "/" + dataPage.total

            handleButtons()
            setFilters()

            binding.buttonLastPage.setOnClickListener {
                mainViewModel.downloadData(dataPage.total - dataPage.current)
            }

            binding.buttonFirstPage.setOnClickListener {
                mainViewModel.downloadData(1 - dataPage.current)
            }
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

                    setFilters()
                }
            ).show(supportFragmentManager,"dialog")
        }

        return true
    }

    fun setFilters(){
        if(!genderFilter.equals("-")) {
            val a = mutableList.filter {
                it.gender.toLowerCase().equals(genderFilter.toLowerCase())
            }
            if(!professionFilter.equals("-")) {
                val b = a.filter {
                    it.profession.toLowerCase().equals(professionFilter.toLowerCase())
                }
                binding.oompaLoompaListRecycler.adapter = OompaLoompaListAdapter(b)
            }else{
                binding.oompaLoompaListRecycler.adapter = OompaLoompaListAdapter(a)
            }
        }else if(!professionFilter.equals("-")) {
            val a = mutableList.filter {
                it.profession.toLowerCase().equals(professionFilter.toLowerCase())
            }
            if(!genderFilter.equals("-")) {
                val b = a.filter {
                    it.gender.toLowerCase().equals(genderFilter.toLowerCase())
                }
                binding.oompaLoompaListRecycler.adapter = OompaLoompaListAdapter(b)
            }else{
                binding.oompaLoompaListRecycler.adapter = OompaLoompaListAdapter(a)
            }
        }else if(genderFilter.equals("-") && professionFilter.equals("-")) {
            binding.oompaLoompaListRecycler.adapter = OompaLoompaListAdapter(mutableList)
        }
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

    private fun enabledProgressDialog(){
        binding.buttonFirstPage.isEnabled = false
        binding.buttonPreviousPage.isEnabled = false
        binding.buttonNextPage.isEnabled = false
        binding.buttonLastPage.isEnabled = false

        progressDialog = ProgressDialog(this)
        progressDialog.show()
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
    }

    private fun handleButtons(){
        if(dataPage.current > 1){ binding.buttonPreviousPage.isEnabled = true }
        else{ binding.buttonPreviousPage.isEnabled = false }
        if(dataPage.current < 20){ binding.buttonNextPage.isEnabled = true }
        else{ binding.buttonNextPage.isEnabled = false }
        if (dataPage.current == 1){ binding.buttonFirstPage.isEnabled = false }
        else{ binding.buttonFirstPage.isEnabled = true }
        if (dataPage.current == dataPage.total){ binding.buttonLastPage.isEnabled = false }
        else{ binding.buttonLastPage.isEnabled = true }
    }

    private fun disabledProgressDialog(){
        progressDialog.dismiss()
        progressDialog.getWindow()!!.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)

        binding.buttonFirstPage.isEnabled = true
        binding.buttonPreviousPage.isEnabled = true
        binding.buttonNextPage.isEnabled = true
        binding.buttonLastPage.isEnabled = true

        handleButtons()
    }

}