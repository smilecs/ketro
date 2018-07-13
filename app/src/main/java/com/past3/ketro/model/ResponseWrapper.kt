package com.past3.ketro.model

data class ResponseWrapper(val page: Int,
                           val pageSize: Int,
                           val totalPageCount: Int,
                           val wkda: Map<String, String>)