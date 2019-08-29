package com.past3.ketroapp.di

import com.past3.ketro.presentation.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @PerView
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

}