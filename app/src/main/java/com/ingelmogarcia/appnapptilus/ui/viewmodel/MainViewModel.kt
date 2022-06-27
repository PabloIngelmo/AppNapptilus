package com.ingelmogarcia.appnapptilus.ui.viewmodel

import androidx.lifecycle.*
import com.ingelmogarcia.appnapptilus.utils.HandleError
import com.ingelmogarcia.appnapptilus.data.model.DataPageModel
import com.ingelmogarcia.appnapptilus.domain.DataPageUseCase
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private var numPage = 0

    private val _dataPageModel = MutableLiveData<DataPageModel>()
    val dataPageModel: LiveData<DataPageModel> get() = _dataPageModel

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean> get() = _isLoading

    private val _catchError = MutableLiveData<HandleError>()
    val catchError:LiveData<HandleError> get() = _catchError

    var dataPageUseCase = DataPageUseCase()


    fun onCreate(){
        downloadDataPage(1)
    }

    fun downloadDataPage(num: Int) {
        numPage += num
        viewModelScope.launch {
            _isLoading.postValue(true)
            var response = dataPageUseCase(numPage)

            response.fold({ error ->
                _catchError.postValue(error)
                _isLoading.postValue(false)
            }
            ,{ datos ->
                    _dataPageModel.postValue(datos)
                    _isLoading.postValue(false)
            })
        }
    }

}