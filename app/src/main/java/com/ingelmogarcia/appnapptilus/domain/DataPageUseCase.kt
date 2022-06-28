package com.ingelmogarcia.appnapptilus.domain

import arrow.core.Either
import com.ingelmogarcia.appnapptilus.utils.HandleError
import com.ingelmogarcia.appnapptilus.data.repository.InfoRepository
import com.ingelmogarcia.appnapptilus.data.model.DataPageModel

class DataPageUseCase {

    private val repository = InfoRepository()

    suspend operator fun invoke(page: Int): Either<HandleError, DataPageModel> = repository.getDataPage(page)

}