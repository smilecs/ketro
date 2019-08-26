package com.past3.ketro.data.di

import com.past3.ketro.data.repository.UserRepositoryimpl
import com.past3.ketro.domain.repository.UserRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepoModule {

    @Binds
    abstract fun provideUserRepo(userRepositoryimpl: UserRepositoryimpl): UserRepository

}