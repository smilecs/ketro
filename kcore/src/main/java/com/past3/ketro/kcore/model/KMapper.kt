package com.past3.ketro.kcore.model

abstract class KMapper<in E, out T> {
    abstract fun mapFrom(from: E): T
}

fun <E, T> KMapper<E, T>.mapObject(wrapper: Wrapper<E>): Wrapper<T> =
        Wrapper(
                data = wrapper.data?.let {
                    mapFrom(it)
                },
                exception = wrapper.exception,
                statusCode = wrapper.statusCode
        )

fun <R, T> KMapper<R, T>.mapObject(kResponse: KResponse<R>): KResponse<T> {
    return when (kResponse) {
        is KResponse.Success -> {
            KResponse.Success(data = kResponse.data?.let {
                mapFrom(it)
            }, statusCode = kResponse.statusCode)
        }
        is KResponse.Failure -> {
            KResponse.Failure(kResponse.exception, kResponse.statusCode)
        }
    }
}