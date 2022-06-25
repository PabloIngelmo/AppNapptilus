package com.ingelmogarcia.appnapptilus

import retrofit2.HttpException
import java.io.IOException

sealed interface HandleError {
    class ServerError(val code: Int) : HandleError
    object ConnectionError : HandleError
    class UnknownError(val message: String) : HandleError
}

fun Throwable.toError(): HandleError = when(this){
    is IOException -> HandleError.ConnectionError
    is HttpException -> HandleError.ServerError(code())
    else -> HandleError.UnknownError(message ?: "")
}