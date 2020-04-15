package com.past3.ketro.kcore.model

sealed class KResponse<T>(val statusCode: StatusCode) {

    class Success<T>(val data: T? = null, statusCode: StatusCode = StatusCode(-1)) : KResponse<T>(statusCode)

    class Failure<T>(val exception: Exception, statusCode: StatusCode = StatusCode(-1)) : KResponse<T>(statusCode)

}