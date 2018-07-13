package com.past3.ketro.api

/*
*ApiErrorHandler class should be extended to handle custom exceptions
*
**/
open class ApiErrorHandler {


    /* Method should be overided to return custom exception type which
    *  would be a sub-type of Exception
    */
   open fun getExceptionType(response: retrofit2.Response<*>): Exception {
        return Exception()
    }


}