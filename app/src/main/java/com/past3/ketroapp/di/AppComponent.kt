package com.past3.ketroapp.di

import com.past3.ketroapp.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            DataModule::class
        ]
)
interface AppComponent : AndroidInjector<Application> {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun build(): AppComponent
    }

}
