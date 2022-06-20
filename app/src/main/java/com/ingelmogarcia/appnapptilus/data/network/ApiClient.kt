package com.ingelmogarcia.appnapptilus.data.network

import com.ingelmogarcia.appnapptilus.data.model.DataPageModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {
    @GET("oompa-loompas")
    suspend fun getDataPage(@Query("page") page:Int): DataPageModel
}
