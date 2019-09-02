package com.past3.ketroapp.di

import dagger.Module

@Module(
        includes = [
            NetModule::class,
            ApiModule::class,
            RepoModule::class
        ]
)
class DataModule