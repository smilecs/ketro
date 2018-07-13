package com.past3.ketro.repo.api

import com.past3.ketro.api.ApiErrorHandler
import com.past3.ketro.api.GenericRequestHandler
import retrofit2.Call
import test.smile.lobby.model.ResponseWrapper
import test.smile.lobby.network.NetModule
import test.smile.lobby.repo.LobbyErrorHandler
import test.smile.lobby.repo.LobbyService

open class LobbyRequest(private val page: Int) : GenericRequestHandler<ResponseWrapper>() {

    private val lobbyService: LobbyService by lazy {
        NetModule.provideRetrofit().create(LobbyService::class.java)
    }
    private val pageSize = 10

    override val errorHandler: ApiErrorHandler = LobbyErrorHandler()

    override fun makeRequest(): Call<ResponseWrapper> {
        return lobbyService.getManufacturers(page, pageSize, "")
    }
}