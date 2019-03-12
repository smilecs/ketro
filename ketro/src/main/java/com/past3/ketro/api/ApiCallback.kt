package com.past3.ketro.api

import com.past3.ketro.model.StatusCode
import retrofit2.Call
import retrofit2.Callback

abstract class ApiCallback<T>(private val errorHandler: ApiErrorHandler) : Callback<T> {

    override fun onResponse(call: Call<T>, response: retrofit2.Response<T>) {
        val statusCode = StatusCode(response.code())
        when (statusCode.code) {
            in 200 until 209 -> {
                handleResponseData(response.body(), statusCode)
            }
            else -> {
                handleError(response, statusCode)
            }
        }
    }

    protected abstract fun handleResponseData(data: T?, statusCode: StatusCode)

    private fun handleError(response: retrofit2.Response<T>, statusCode: StatusCode) {
        handleException(errorHandler.getExceptionType(response), statusCode)
    }

    protected abstract fun handleException(ex: Exception, statusCode: StatusCode)

    override fun onFailure(call: Call<T>, t: Throwable) {
        if (t is Exception) {
            handleException(t, StatusCode(-1))
        } else {
            //do something else
        }
    }

}