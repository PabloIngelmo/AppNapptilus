package com.ingelmogarcia.appnapptilus.ui.viewmodel

import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ingelmogarcia.appnapptilus.HandleError
import com.ingelmogarcia.appnapptilus.R
import com.ingelmogarcia.appnapptilus.data.model.DataPageModel
import com.ingelmogarcia.appnapptilus.data.model.OompaLoompaDetailModel
import com.ingelmogarcia.appnapptilus.domain.OompaLoompaDetailUseCase
import kotlinx.coroutines.launch

class OompaLoompaDetailViewModel: ViewModel() {

    private val TAG_CONNECTION_ERROR = "ConnectionError"
    private val TAG_SERVER_ERROR = "ServerError"
    private val TAG_UNKNOWN_ERROR = "UnknownError"

    private val _oompaLoompaDetailModel = MutableLiveData<OompaLoompaDetailModel>()
    val oompaLoompaDetailModel: LiveData<OompaLoompaDetailModel> get() = _oompaLoompaDetailModel

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    var oompaLoompaDetailUseCase = OompaLoompaDetailUseCase()


    fun downloadOompaLoompaDetail(oompaLoompaId: Int) {
        viewModelScope.launch {
            _isLoading.postValue(true)
            var response = oompaLoompaDetailUseCase(oompaLoompaId)

            response.fold({ error ->
                when(error){
                    is HandleError.ConnectionError -> Log.e(TAG_CONNECTION_ERROR, Resources.getSystem().getString(
                        R.string.ConnectionErrorMessage))
                    is HandleError.ServerError -> Log.e(TAG_SERVER_ERROR,
                        Resources.getSystem().getString(R.string.ServerErrorMessage))
                    is HandleError.UnknownError -> Log.e(TAG_UNKNOWN_ERROR, Resources.getSystem().getString(
                        R.string.UnknownErrorMessage))
                }
                _isLoading.postValue(false)
            }
                ,{ datos ->
                    _oompaLoompaDetailModel.postValue(datos)
                    _isLoading.postValue(false)
                })
        }
    }
}