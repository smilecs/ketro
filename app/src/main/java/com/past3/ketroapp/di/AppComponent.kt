package com.past3.ketroapp.di

import com.past3.ketroapp.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AndroidSupportInjectionModule::class,
            DataModule::class,
            ViewModelModule::class,
            ActivityModule::class
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
