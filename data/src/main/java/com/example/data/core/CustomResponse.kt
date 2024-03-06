package com.example.data.core

sealed class CustomResponse<out T: Any> {

    object Loading : CustomResponse<Nothing>()

    data class Success<out T: Any>(val result: T) : CustomResponse<T>()

    data class Error(val error: Throwable) : CustomResponse<Nothing>()

}
