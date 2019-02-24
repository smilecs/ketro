package com.past3.ketro.model

import okhttp3.ResponseBody

open class Kexception(val errorBody: ResponseBody?, message: String?, cause: Throwable?) : Exception(message, cause)