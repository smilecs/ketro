package com.past3.ketroapp

import retrofit2.Response

/*class CoRountineSampleRequest {
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

*/