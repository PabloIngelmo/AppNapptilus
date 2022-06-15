package com.ingelmogarcia.appnapptilus.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ingelmogarcia.appnapptilus.model.OompaLoompaModel

class OompaLoompaViewModel : ViewModel() {

    private val _oompaLoompaModel = MutableLiveData<OompaLoompaModel>()
    val oompaLoompaModel: LiveData<OompaLoompaModel> get() = _oompaLoompaModel

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean> get() = _isLoading
}