package com.ingelmogarcia.appnapptilus.data

import arrow.core.Either
import com.ingelmogarcia.appnapptilus.HandleError
import com.ingelmogarcia.appnapptilus.data.model.DataPageModel
import com.ingelmogarcia.appnapptilus.data.model.DataPageProvider
import com.ingelmogarcia.appnapptilus.data.network.RemoteDataSource

class InfoRepository {

    private val remoteDataSource = RemoteDataSource()

    suspend fun getDataPage(page: Int): Either<HandleError, DataPageModel> {
        return remoteDataSource.getDataPage(page)
    }
}