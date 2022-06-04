package com.past3.ketro.kcore.model

/**
 *   @param statusCode Returns the request state or -1 if request does not happen due to internet
 *   or similar connectivity issues
 */

@JvmInline
value class StatusCode(val code: Int)