package com.past3.ketro

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class ResponseModel(
        @SerializedName("total_count")
        val totalCount: Int,
        @SerializedName("incomplete_results")
        val incompleteResults: Boolean,
        val items: List<Items>
)
{
    @Parcelize
    data class Items(
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
    ) : Parcelable
}

