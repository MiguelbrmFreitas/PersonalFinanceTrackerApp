package com.example.data.core

sealed class Resource<T>(
    open val data: T?,
    open val error: Throwable? = null
) {

    data class Success<T>(override val data: T?) : Resource<T>(data, null)

    data class Error<T>(
        override val data: T?,
        override val error: Throwable) : Resource<T>(null, error)

    data class Loading<T>(override val data: T? = null) : Resource<T>(data)
}

