package com.past3.ketro.api

import android.arch.lifecycle.MutableLiveData
import com.past3.ketro.kcore.model.KResponse

class LiveDataHandler(private val liveData: MutableLiveData<Exception>) {

    fun <T> emit(kResponse: KResponse<T>, mutableLiveData: MutableLiveData<T>) {
        when (kResponse) {
            is KResponse.Success<T> -> {
                mutableLiveData.value = kResponse.data
            }
            is KResponse.Failure -> {
                liveData.value = kResponse.exception
            }
        }
    }

    fun <T> parse(kResponse: KResponse<T>, action: (T?) -> Unit) {
        when (kResponse) {
            is KResponse.Success<T> -> {
                action(kResponse.data)
            }
            is KResponse.Failure -> {
                liveData.value = kResponse.exception
            }
        }
    }

}