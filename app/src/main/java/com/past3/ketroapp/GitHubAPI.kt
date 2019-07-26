package com.past3.ketroapp

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubAPI {
    @GET("/search/users")
    fun searchUser(@Query("q") query: String): Call<ResponseModel>

    @GET("/search/uss")
    suspend fun searchUse(@Query("q") query: String): Response<ResponseModel>
}