package com.ingelmogarcia.appnapptilus.domain

import arrow.core.Either
import com.ingelmogarcia.appnapptilus.utils.HandleError
import com.ingelmogarcia.appnapptilus.data.repository.InfoRepository
import com.ingelmogarcia.appnapptilus.data.model.OompaLoompaDetailModel

class OompaLoompaDetailUseCase{

    private val repository = InfoRepository()

    suspend operator fun invoke(oompaLoompaId: Int): Either<HandleError, OompaLoompaDetailModel> = repository.getOompaLoompaDetail(oompaLoompaId)

}