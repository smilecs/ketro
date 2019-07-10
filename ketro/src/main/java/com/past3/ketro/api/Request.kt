package com.past3.ketro.api

import retrofit2.Response

abstract class Request<T> {

    protected abstract suspend fun makeRequest(): Response<T>

    open val errorHandler: ApiErrorHandler = ApiErrorHandler()

    open suspend fun doRequest() {
        println(makeRequest())
    }

}