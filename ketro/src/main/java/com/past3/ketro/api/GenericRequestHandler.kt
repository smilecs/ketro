package com.past3.ketro.api

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.past3.ketro.model.StatusCode
import com.past3.ketro.model.Wrapper
import retrofit2.Call

abstract class GenericRequestHandler<R> {
    /**
     * Perform API request
     *
     * @return Retrofit call
     */
    protected abstract fun makeRequest(): Call<R>

    /*
    Override errorHandler() to return custom ApiErrorHandler object
    where error/exception creation mapping resides.
     */
    open val errorHandler: ApiErrorHandler = ApiErrorHandler()

    /*
    Override to give custom implementation of Retrofit enque function
     */
    open fun doRequestInternal(liveData: MutableLiveData<Wrapper<R>>) {
        makeRequest().enqueue(object : ApiCallback<R>(errorHandler) {
            override fun handleResponseData(data: R?, statusCode: StatusCode) {
                liveData.value = Wrapper(data = data, statusCode = statusCode)
            }

            override fun handleException(ex: Exception, statusCode: StatusCode) {
                liveData.value = Wrapper(ex, statusCode = statusCode)
            }
        })
    }

    fun doRequest(): LiveData<Wrapper<R>> {
        val liveData = MutableLiveData<Wrapper<R>>()
        executeRequest(liveData)
        return liveData
    }

    open fun executeRequest(liveData: MutableLiveData<Wrapper<R>>) {
        doRequestInternal(liveData)
    }
}