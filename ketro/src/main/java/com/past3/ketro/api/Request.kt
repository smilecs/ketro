package com.past3.ketro.api

import com.past3.ketro.api.model.KResponse
import com.past3.ketro.api.model.StatusCode
import com.past3.ketro.api.model.Wrapper
import retrofit2.Response

abstract class Request<out T : Any>(
    private val errorHandler: ApiErrorHandler = ApiErrorHandler()
) {
    abstract suspend fun apiRequest(): Response<out T>

    open suspend fun doRequest(): Wrapper<out T> {
        val resp = apiRequest()
        val statusCode = StatusCode(resp.code())
        return when (resp.code()) {
            in 200 until 209 -> {
                handleResponseData(resp.body(), statusCode)
            }

            else -> {
                handleError(resp, statusCode)
            }
        }
    }

    open suspend fun execute(): KResponse<T> {
        val resp: Response<out T> = apiRequest()
        val statusCode = StatusCode(resp.code())
        return when (resp.code()) {
            in 200 until 209 -> {
                KResponse.Success(resp.body(), statusCode)
            }

            else -> {
                KResponse.Failure(errorHandler.getExceptionType(resp), statusCode)
            }
        }
    }

    private fun <T> handleResponseData(data: T?, statusCode: StatusCode): Wrapper<out T> {
        return Wrapper(data = data, statusCode = statusCode)
    }

    private fun <T> handleError(response: Response<T>, statusCode: StatusCode): Wrapper<out T> {
        return Wrapper(errorHandler.getExceptionType(response), statusCode = statusCode)
    }
}
