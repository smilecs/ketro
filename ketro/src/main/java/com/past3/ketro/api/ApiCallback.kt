package com.past3.ketro.api

import retrofit2.Call
import retrofit2.Callback

abstract class ApiCallback<T>(private val errorHandler: ApiErrorHandler) : Callback<T> {

    override fun onResponse(call: Call<T>, response: retrofit2.Response<T>) =
            if (response.body() != null) {
                handleResponseData(response.body()!!)
            } else {
                handleError(response)
            }

    protected abstract fun handleResponseData(data: T)

    private fun handleError(response: retrofit2.Response<T>) {
        handleException(errorHandler.getExceptionType(response))
    }

    protected abstract fun handleException(t: Exception)

    override fun onFailure(call: Call<T>, t: Throwable) {
        if (t is Exception) {
            handleException(t)
        } else {
            //do something else
        }
    }

}