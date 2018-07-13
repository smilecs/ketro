package com.past3.ketro.repo

import com.past3.ketro.api.ApiErrorHandler
import retrofit2.Response

class LobbyErrorHandler : ApiErrorHandler() {

    override fun getExceptionType(response: Response<*>): Exception {
        return when (response.code()) {
            LOGIN_ERROR -> LoginException()
            UPDATE_ERROR -> UpdateException()
            else -> Exception()
        }
    }

    companion object ErrorConfig {
        const val LOGIN_ERROR = 401
        const val UPDATE_ERROR = 404

        class LoginException : Exception() {
            override val message = "Error processing login details"
        }

        class UpdateException : Exception() {
            override val message = "Error updating details"
        }
    }

}