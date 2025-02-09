package com.past3.ketro.api

import okhttp3.ResponseBody

open class KException(val errorBody: ResponseBody?,
                      message: String?,
                      cause: Throwable?,
                      val statusCode: StatusCode
) : Exception(message, cause)