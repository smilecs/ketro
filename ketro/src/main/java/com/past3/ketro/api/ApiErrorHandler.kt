package com.past3.ketro.api

/*
*ApiErrorHandler class should be extended to handle custom exceptions
*
**/
open class ApiErrorHandler {
    /* Method should be overridden to return custom exception type which
    *  would be a sub-type of Exception or to have the response body,
    *  return a sub-type of kexception()
    */
    open fun getExceptionType(response: retrofit2.Response<*>): Exception {
        return KException(
            response.errorBody(),
            response.message(),
            null,
            StatusCode(response.code())
        )
    }
}
