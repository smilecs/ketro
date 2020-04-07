package com.past3.ketro.kcore.model

sealed class KResponse(val statusCode: StatusCode = StatusCode(-1)) {

    class Success<T>(val data: T? = null) : KResponse()

    class Failure(val exception: Exception? = null)

}