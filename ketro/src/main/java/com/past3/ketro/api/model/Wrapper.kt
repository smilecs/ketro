package com.past3.ketro.api.model

/**
 *    Wrapper for Api responses
 *   @param exception Returns thrown exception or null if successful request
 *   @param data Returns response object or null if empty object or not successful
 *   @param statusCode Returns the request state or -1 if request does not happen due to internet
 *   or similar connectivity issues
 */
class Wrapper<T>(
        val exception: Exception? = null,
        val data: T? = null,
        val statusCode: StatusCode = StatusCode(-1)
)

@JvmInline
value class StatusCode(val code: Int)