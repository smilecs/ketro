package com.past3.ketroapp.di

import com.past3.ketro.data.di.ApiModule
import com.past3.ketro.data.di.NetModule
import dagger.Module

@Module(
        includes = [
            NetModule::class,
            ApiModule::class
        ]
)

class DataModule