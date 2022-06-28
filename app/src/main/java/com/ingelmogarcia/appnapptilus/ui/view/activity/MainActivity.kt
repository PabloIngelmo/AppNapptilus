package com.ingelmogarcia.appnapptilus.ui.view.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.ingelmogarcia.appnapptilus.R
import com.ingelmogarcia.appnapptilus.data.model.DataPageModel
import com.ingelmogarcia.appnapptilus.data.model.OompaLoompaItemModel
import com.ingelmogarcia.appnapptilus.databinding.ActivityMainBinding
import com.ingelmogarcia.appnapptilus.ui.adapter.OompaLoompaListAdapter
import com.ingelmogarcia.appnapptilus.ui.components.FiltersDialog
import com.ingelmogarcia.appnapptilus.ui.viewmodel.MainViewModel
import com.ingelmogarcia.appnapptilus.utils.HandleError

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener, View.OnClickListener {

    private val TAG_CONNECTION_ERROR = "ConnectionError"
    private val TAG_SERVER_ERROR = "ServerError"
    private val TAG_UNKNOWN_ERROR = "UnknownError"
    private val TAG_DIALOG = "dialog"
    private val KEY_OOMPALOOMPAID = "oompaLoompaId"

    private lateinit var binding: ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()
    val oompaLoompaList : MutableList<OompaLoompaItemModel> = mutableListOf()
    private var dataPage: DataPageModel? = null
    private lateinit var searchView: SearchView
    private lateinit var progressDialog: ProgressDialog
    private var genderFilter = "-"
    private var professionFilter = "-"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTitle(getString(R.string.OompaLoompasTitle))
        setButtonText()

        viewModel.onCreate()
        binding.oompaLoompaListRecycler.layoutManager = GridLayoutManager(this,5)

        viewModel.isLoading.observe(this, Observer(this::handleProgressDialog))
        viewModel.dataPageModel.observe(this, Observer(this::updateUI))
        viewModel.catchError.observe(this, Observer(this::handleError))
    }

    fun setButtonText(){
        binding.buttonPreviousPage.text = "<"
        binding.buttonNextPage.text = ">"
        binding.buttonFirstPage.text = "<<"
        binding.buttonLastPage.text = ">>"
    }

    fun updateUI(dataPage: DataPageModel){
        this.dataPage = dataPage
        oompaLoompaList.clear()
        for (i in dataPage.results){
            oompaLoompaList.add(OompaLoompaItemModel(i.urlImage,i.first_name,i.gender, i.profession,i.id))
        }
        binding.oompaLoompaListRecycler.adapter = OompaLoompaListAdapter(oompaLoompaList, ::onItemClicked)
        binding.textviewNumPage.text = dataPage.current.toString() + "/" + dataPage.total

        handleButtons()
        setFilters()

        binding.buttonLastPage.setOnClickListener(this)
        binding.buttonFirstPage.setOnClickListener(this)
        binding.buttonNextPage.setOnClickListener(this)
        binding.buttonPreviousPage.setOnClickListener(this)
    }

    fun handleProgressDialog(boolean: Boolean){
        if(boolean){
            enabledProgressDialog()
        }else{
            disabledProgressDialog()
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
            ).show(supportFragmentManager,TAG_DIALOG)
        }

        return true
    }

    fun setFilters(){
        if(!genderFilter.equals("-")) {
            val a = oompaLoompaList.filter {
                it.gender.toLowerCase().equals(genderFilter.toLowerCase())
            }
            if(!professionFilter.equals("-")) {
                val b = a.filter {
                    it.profession.toLowerCase().equals(professionFilter.toLowerCase())
                }
                binding.oompaLoompaListRecycler.adapter = OompaLoompaListAdapter(b,::onItemClicked)
            }else{
                binding.oompaLoompaListRecycler.adapter = OompaLoompaListAdapter(a,::onItemClicked)
            }
        }else if(!professionFilter.equals("-")) {
            val a = oompaLoompaList.filter {
                it.profession.toLowerCase().equals(professionFilter.toLowerCase())
            }
            if(!genderFilter.equals("-")) {
                val b = a.filter {
                    it.gender.toLowerCase().equals(genderFilter.toLowerCase())
                }
                binding.oompaLoompaListRecycler.adapter = OompaLoompaListAdapter(b,::onItemClicked)
            }else{
                binding.oompaLoompaListRecycler.adapter = OompaLoompaListAdapter(a,::onItemClicked)
            }
        }else if(genderFilter.equals("-") && professionFilter.equals("-")) {
            binding.oompaLoompaListRecycler.adapter = OompaLoompaListAdapter(oompaLoompaList,::onItemClicked)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        val a = oompaLoompaList.filter { it.title.toLowerCase().contains(query.toString().toLowerCase())}
        binding.oompaLoompaListRecycler.adapter = OompaLoompaListAdapter(a,::onItemClicked)
        dismissKeyboard()
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        val a = oompaLoompaList.filter { it.title.toLowerCase().contains(newText.toString().toLowerCase())}
        binding.oompaLoompaListRecycler.adapter = OompaLoompaListAdapter(a,::onItemClicked)
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
        if(dataPage!=null){
            if(dataPage!!.current > 1){ binding.buttonPreviousPage.isEnabled = true }
            else{ binding.buttonPreviousPage.isEnabled = false }
            if(dataPage!!.current < 20){ binding.buttonNextPage.isEnabled = true }
            else{ binding.buttonNextPage.isEnabled = false }
            if (dataPage!!.current == 1){ binding.buttonFirstPage.isEnabled = false }
            else{ binding.buttonFirstPage.isEnabled = true }
            if (dataPage!!.current == dataPage!!.total){ binding.buttonLastPage.isEnabled = false }
            else{ binding.buttonLastPage.isEnabled = true }
        }

    }

    private fun handleError(error: HandleError){
        when(error){
            is HandleError.ConnectionError -> {
                Log.e(TAG_CONNECTION_ERROR, getString(R.string.ConnectionErrorMessage))
                binding.textViewError.text = getString(R.string.ConnectionErrorMessage)
            }
            is HandleError.ServerError -> {
                Log.e(TAG_SERVER_ERROR, getString(R.string.ServerErrorMessage))
                binding.textViewError.text = getString(R.string.ServerErrorMessage)
            }
            is HandleError.UnknownError -> {
                Log.e(TAG_UNKNOWN_ERROR, getString(R.string.UnknownErrorMessage))
                binding.textViewError.text = getString(R.string.UnknownErrorMessage)
            }
        }
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

    private fun onItemClicked(oompaLoompaItemModel: OompaLoompaItemModel){
        var intent = Intent(this, OompaLoompaDetailActivity::class.java)
        intent.putExtra(KEY_OOMPALOOMPAID, oompaLoompaItemModel.id)
        startActivity(intent)
    }

    override fun onClick(p0: View?) {
        if(dataPage != null) {
            when (p0?.id) {
                R.id.buttonLastPage -> viewModel.downloadDataPage(dataPage!!.total - dataPage!!.current)
                R.id.buttonFirstPage -> viewModel.downloadDataPage(1 - dataPage!!.current)
                R.id.buttonNextPage -> viewModel.downloadDataPage(1)
                R.id.buttonPreviousPage -> viewModel.downloadDataPage(-1)
            }
        }
    }


}