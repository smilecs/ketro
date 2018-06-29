package com.past3.ketro.api

import android.arch.lifecycle.Observer
import com.past3.ketro.model.Wrapper

abstract class Kobserver<T> : Observer<Wrapper<T>> {

    override fun onChanged(wrapper: Wrapper<T>?) {
        finish()
        wrapper?.let {
            it.exception?.let {
                onException(it)
            }
            it.data?.let {
                onSuccess(it)
            }
            return
        }
        onException(Exception("Something went wrong"))
    }
    /**
     * Method to be called on successful load of data. This method is guaranteed to contain the returned data
     *
     * @param data
     */
    abstract fun onSuccess(data: T)

    /**
     * Method to handle exception that occurs during request processing.
     * @param exception
     */
    abstract fun onException(exception: Exception)

    /**
     * Triggered when internet request is cancelled by user.
     */

    fun finish() {

    }
}