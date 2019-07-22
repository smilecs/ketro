package com.past3.ketro

import com.past3.ketro.api.Request
import com.past3.ketro.model.Wrapper
import retrofit2.Response

class CoRountineSampleRequest {
    private val gitHubAPI: GitHubAPI by lazy {
        NetworkModule.createRetrofit().create(GitHubAPI::class.java)
    }

    suspend fun requestGithubUser(): Wrapper<ResponseModel> {
        val req = object : Request<ResponseModel>() {
            override suspend fun makeRequest(): Response<ResponseModel> {
                return gitHubAPI.searchUse("")
            }
        }
        return req.doRequest()
    }

}