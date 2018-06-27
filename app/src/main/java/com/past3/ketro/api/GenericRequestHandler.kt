package com.past3.ketro.api

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.past3.ketro.model.Wrapper
import retrofit2.Call

abstract class GenericRequestHandler<R> {
    /**
     * Perform API request
     *
     * @return Retrofit call
     */
    protected abstract fun makeRequest(): Call<R>

    protected fun doRequestInternal(liveData: MutableLiveData<Wrapper<R>>, Wrapper: Wrapper<R>) {
        makeRequest().enqueue(object : ApiCallback<R>(getErrorHandler()) {
            override fun handleResponseData(data: R) {
                Wrapper.data = data
                liveData.value = Wrapper
            }

            override fun handleException(t: Exception) {
                Wrapper.exception = t
                liveData.value = Wrapper
            }
        })
    }

    fun doRequest(): LiveData<Wrapper<R>> {
        val liveData = MutableLiveData<Wrapper<R>>()
        val wrapper = Wrapper<R>()
        executeRequest(liveData, wrapper)
        return liveData
    }

    fun executeRequest(liveData: MutableLiveData<Wrapper<R>>, wrapper: Wrapper<R>) {
        doRequestInternal(liveData, wrapper)
    }

    /*
    Override getErrorHandler() to return custom ApiErrorHandler object
    where error/exception creation mapping resides.
     */
    fun getErrorHandler():ApiErrorHandler?{
        return ApiErrorHandler()
    }
}