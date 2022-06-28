package com.ingelmogarcia.appnapptilus.data.repository

import arrow.core.Either
import com.ingelmogarcia.appnapptilus.utils.HandleError
import com.ingelmogarcia.appnapptilus.data.model.DataPageModel
import com.ingelmogarcia.appnapptilus.data.model.OompaLoompaDetailModel
import com.ingelmogarcia.appnapptilus.data.network.RemoteDataSource

class InfoRepository {

    private val remoteDataSource = RemoteDataSource()

    suspend fun getDataPage(page: Int): Either<HandleError, DataPageModel> {
        return remoteDataSource.getDataPage(page)
    }

    suspend fun getOompaLoompaDetail(oompaLoompaId: Int): Either<HandleError, OompaLoompaDetailModel> {
        return remoteDataSource.getOompaLoompaDetail(oompaLoompaId)
    }
}