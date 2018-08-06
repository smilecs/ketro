package com.past3.ketro

import com.past3.ketro.api.ApiErrorHandler
import com.past3.ketro.api.GenericRequestHandler
import retrofit2.Call

class GitHubRequest(private val name: String) : GenericRequestHandler<ResponseModel>(){
    private val gitHubAPI: GitHubAPI by lazy {
        NetworkModule.createRetrofit().create(GitHubAPI::class.java)
    }

    override val errorHandler: ApiErrorHandler = GitHubErrorHandler()

    override fun makeRequest(): Call<ResponseModel> {
        return gitHubAPI.searchUser(name)
    }
}