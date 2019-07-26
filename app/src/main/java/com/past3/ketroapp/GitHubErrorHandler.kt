package com.past3.ketroapp

import com.past3.ketro.api.ApiErrorHandler
import retrofit2.Response

class GitHubErrorHandler : ApiErrorHandler() {

    override fun getExceptionType(response: Response<*>): Exception {
        return when (response.code()) {
            NETWORK_ERROR -> NetworkException()
            GITHUB_ERROR -> GitHubException()
            else -> Exception()
        }
    }

    companion object ErrorConfig {
        const val NETWORK_ERROR = 401
        const val GITHUB_ERROR = 404

        class NetworkException : Exception() {
            override val message = "Error due to network"
        }

        class GitHubException : Exception() {
            override val message = "Error from GitHub"
        }
    }
}