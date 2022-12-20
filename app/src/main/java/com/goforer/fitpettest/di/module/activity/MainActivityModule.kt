package com.goforer.fitpettest.di.module.activity

import com.goforer.fitpettest.di.module.fragment.home.HomeFragmentModule
import com.goforer.fitpettest.presentation.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(
        modules = [
            HomeFragmentModule::class
        ]
    )

    abstract fun contributeMainActivity(): MainActivity
}