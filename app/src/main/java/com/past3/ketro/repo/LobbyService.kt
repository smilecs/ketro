package com.past3.ketro.repo

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import test.smile.lobby.model.ResponseWrapper
import test.smile.lobby.network.Urls

interface LobbyService {

    @GET(Urls.MANUFACTURER)
    fun getManufacturers(@Query("page") page: Int,
                         @Query("pageSize") pageSize: Int,
                         @Query("wa_key") key: String): Call<ResponseWrapper>
}