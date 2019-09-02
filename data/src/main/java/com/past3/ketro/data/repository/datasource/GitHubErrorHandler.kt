package com.past3.ketro.data.repository.datasource

import com.past3.ketro.api.ApiErrorHandler
import com.past3.ketro.domain.entities.ErrorConfig
import com.past3.ketro.domain.entities.ErrorConfig.GITHUB_ERROR
import com.past3.ketro.domain.entities.ErrorConfig.GitHubException
import com.past3.ketro.domain.entities.ErrorConfig.NETWORK_ERROR
import retrofit2.Response

class GitHubErrorHandler : ApiErrorHandler() {

    override fun getExceptionType(response: Response<*>): Exception {
        return when (response.code()) {
            NETWORK_ERROR -> ErrorConfig.NetworkException()
            GITHUB_ERROR -> GitHubException()
            else -> Exception()
        }
    }

}