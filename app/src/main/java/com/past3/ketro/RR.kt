package com.past3.ketro

import com.past3.ketro.api.ApiErrorHandler
import com.past3.ketro.api.Request

class RR : Request<ResponseModel>() {
    private val gitHubAPI: GitHubAPI by lazy {
        NetworkModule.createRetrofit().create(GitHubAPI::class.java)
    }

    override val errorHandler: ApiErrorHandler = GitHubErrorHandler()

    override suspend fun makeRequest(): ResponseModel {
        return gitHubAPI.searchUse("smile")
    }
}