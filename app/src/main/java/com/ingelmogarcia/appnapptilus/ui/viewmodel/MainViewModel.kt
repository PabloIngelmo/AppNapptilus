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
import com.ingelmogarcia.appnapptilus.data.model.DataPageProvider
import com.ingelmogarcia.appnapptilus.data.model.OompaLoompaModel
import com.ingelmogarcia.appnapptilus.domain.DataPageUseCase
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val TAG_CONNECTION_ERROR = "ConnectionError"
    private val TAG_SERVER_ERROR = "ServerError"
    private val TAG_UNKNOWN_ERROR = "UnknownError"
    private var numPage = 0

    private val _dataPageModel = MutableLiveData<DataPageModel>()
    val dataPageModel: LiveData<DataPageModel> get() = _dataPageModel

    private val _oompaLoompaModel = MutableLiveData<OompaLoompaModel>()
    val oompaLoompaModel: LiveData<OompaLoompaModel> get() = _oompaLoompaModel

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean> get() = _isLoading

    var dataPageUseCase = DataPageUseCase()


    fun onCreate(){
        downloadData(1)
    }

    fun downloadData(num: Int) {
        numPage += num
        viewModelScope.launch {
            var response = dataPageUseCase(numPage)

            response.fold({ error ->
                when(error){
                    is HandleError.ConnectionError -> Log.e(TAG_CONNECTION_ERROR, Resources.getSystem().getString(R.string.ConnectionErrorMessage))
                    is HandleError.ServerError -> Log.e(TAG_SERVER_ERROR,Resources.getSystem().getString(R.string.ServerErrorMessage))
                    is HandleError.UnknownError -> Log.e(TAG_UNKNOWN_ERROR, Resources.getSystem().getString(R.string.UnknownErrorMessage))
                 }
            }
            ,{ datos -> /*DataPageProvider.dataPage = datos*/
                    _dataPageModel.postValue(datos)
            })
        }
    }

}