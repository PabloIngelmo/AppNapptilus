package com.ingelmogarcia.appnapptilus.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ingelmogarcia.appnapptilus.HandleError
import com.ingelmogarcia.appnapptilus.data.model.DataPageModel
import com.ingelmogarcia.appnapptilus.data.model.DataPageProvider
import com.ingelmogarcia.appnapptilus.data.model.OompaLoompaModel
import com.ingelmogarcia.appnapptilus.domain.DataPageUseCase
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

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
                    is HandleError.ServerError -> error.code
                    is HandleError.UnknownError -> error.message
                    is HandleError.ConnectivityError -> "Error de conexión. comprueba que tu dispositivo tiene conexión a internet."
                }
            }
            ,{ datos -> /*DataPageProvider.dataPage = datos*/
                    _dataPageModel.postValue(datos)
            })
        }
    }

}