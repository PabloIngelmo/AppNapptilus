package com.ingelmogarcia.appnapptilus

import retrofit2.HttpException
import java.io.IOException

sealed interface HandleError {
    class ServerError(val code: Int) : HandleError
    object ConnectivityError : HandleError
    class UnknownError(val message: String) : HandleError
}

fun Throwable.toError(): HandleError = when(this){
    is IOException -> HandleError.ConnectivityError
    is HttpException -> HandleError.ServerError(code())
    else -> HandleError.UnknownError(message ?: "")
}