package com.example.mypictionary.util

sealed class ApiResultWrapper<T>(
    open val data: T? = null, open val message: String? = null, open val errorCode: Int? = null
) {
    data class Success<T>(override val data: T?) : ApiResultWrapper<T>(data)
    data class Error<T>(override val message: String?, override val errorCode: Int?, override val data: T? = null) : ApiResultWrapper<T>(data, message, errorCode)
    data class Loading<T>(override val message: String?, override val data: T? = null) : ApiResultWrapper<T>(data, message)
}

