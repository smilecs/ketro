package com.past3.ketro.domain.entities

object ErrorConfig {

    const val NETWORK_ERROR = 401
    const val GITHUB_ERROR = 404

    class NetworkException : Exception() {
        override val message = "Error due to network"
    }

    class GitHubException : Exception() {
        override val message = "Error from GitHub"
    }

}
