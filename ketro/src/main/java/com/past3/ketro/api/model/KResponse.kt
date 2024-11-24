package com.past3.ketro.api.model

sealed class KResponse<out T>(val statusCode: StatusCode) {

    class Success<T>(val data: T? = null, statusCode: StatusCode = StatusCode(-1)) :
        KResponse<T>(statusCode)

    class Failure(val exception: Exception, statusCode: StatusCode = StatusCode(-1)) :
        KResponse<Nothing>(statusCode)
}
