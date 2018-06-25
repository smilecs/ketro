package com.past3.ketro.model

class Wrapper<T> {
    var exception: Exception? = null
    var data: T? = null
    var cancelled = false
}