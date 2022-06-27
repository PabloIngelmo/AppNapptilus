package com.ingelmogarcia.appnapptilus.domain

import arrow.core.Either
import com.ingelmogarcia.appnapptilus.HandleError
import com.ingelmogarcia.appnapptilus.data.InfoRepository
import com.ingelmogarcia.appnapptilus.data.model.DataPageModel
import com.ingelmogarcia.appnapptilus.data.model.OompaLoompaDetailModel

class OompaLoompaDetailUseCase{

    private val repository = InfoRepository()

    suspend operator fun invoke(oompaLoompaId: Int): Either<HandleError, OompaLoompaDetailModel> = repository.getOompaLoompaDetail(oompaLoompaId)

}