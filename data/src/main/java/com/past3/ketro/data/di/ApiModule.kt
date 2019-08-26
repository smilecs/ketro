package com.past3.ketro.data.di

import com.past3.ketro.data.repository.GitHubAPI
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class ApiModule {

    @Provides
    fun githubAPI(retrofit: Retrofit): GitHubAPI =
            retrofit.create(GitHubAPI::class.java)

}