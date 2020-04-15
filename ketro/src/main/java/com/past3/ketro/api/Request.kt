package com.past3.ketro.api

import android.support.annotation.VisibleForTesting
import com.past3.ketro.kcore.model.KResponse
import com.past3.ketro.kcore.model.StatusCode
import com.past3.ketro.kcore.model.Wrapper
import retrofit2.Response

abstract class Request<T>(
        private val errorHandler: ApiErrorHandler = ApiErrorHandler()
) {

    abstract suspend fun apiRequest(): Response<T>

    open suspend fun doRequest(): Wrapper<T> {
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
        val resp = apiRequest()
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

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun handleResponseData(data: T?, statusCode: StatusCode): Wrapper<T> {
        return Wrapper(data = data, statusCode = statusCode)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun handleError(response: Response<T>, statusCode: StatusCode): Wrapper<T> {
        return Wrapper(errorHandler.getExceptionType(response), statusCode = statusCode)
    }

}