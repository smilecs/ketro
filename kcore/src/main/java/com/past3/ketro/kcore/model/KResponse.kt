package com.past3.ketro.kcore.model

sealed class KResponse<out T : Any>(val statusCode: StatusCode) {

    class Success<out T : Any>(val data: T? = null, statusCode: StatusCode = StatusCode(-1)) : KResponse<T>(statusCode)

    class Failure(val exception: Exception, statusCode: StatusCode = StatusCode(-1)) : KResponse<Nothing>(statusCode)

}