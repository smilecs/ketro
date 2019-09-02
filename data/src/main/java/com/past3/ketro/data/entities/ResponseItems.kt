package com.past3.ketro.data.entities


data class ResponseItems(
        val login: String,
        val id: Int,
        val node_id: String,
        val avatar_url: String,
        val gravatar_url: String,
        val url: String,
        val html_url: String,
        val followers_url: String,
        val subscriptions_url: String,
        val organizations_url: String,
        val repos_url: String,
        val received_events_url: String,
        val type: String,
        val score: Double
)


