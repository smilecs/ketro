package com.past3.ketro.api

import com.past3.ketro.kcore.model.StatusCode
import com.past3.ketro.kcore.model.Wrapper
import retrofit2.Response

abstract class Request<T> {

    open var errorHandler: ApiErrorHandler = ApiErrorHandler()

    protected abstract suspend fun makeRequest(): Response<T>


    suspend fun doRequest(): Wrapper<T> {
        return makeRequest().run {
            val statusCode = StatusCode(code())
            when (statusCode.code) {
                in 200 until 209 -> {
                    handleResponseData(body(), statusCode)
                }
                else -> {
                    handleError(this, statusCode)
                }
            }

        }
    }

    private fun handleResponseData(data: T?, statusCode: StatusCode): Wrapper<T> {
        return Wrapper(data = data, statusCode = statusCode)
    }

    private fun handleError(response: Response<T>, statusCode: StatusCode): Wrapper<T> {
        return Wrapper(errorHandler.getExceptionType(response), statusCode = statusCode)
    }

}