package com.past3.ketro.data.repository.datasource

import com.past3.ketro.api.Request
import com.past3.ketro.data.entities.ResponseItems
import com.past3.ketro.kcore.model.Wrapper
import retrofit2.Response
import javax.inject.Inject

class GetUserDataSourceRemote @Inject constructor(private val gitHubAPI: GitHubAPI) {

    suspend fun requestGithubUser(user: String): Wrapper<ResponseItems> {
        val req = object : Request<ResponseItems>(GitHubErrorHandler()) {
            override suspend fun apiRequest(): Response<ResponseItems> =
                    gitHubAPI.searchUse(user)
        }
        return req.doRequest()
    }

}