package com.past3.ketro.kcore.model

abstract class KMapper<in E, T> {

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