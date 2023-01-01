package com.goforer.weather.di.module.activity

import com.goforer.weather.di.module.fragment.home.HomeFragmentModule
import com.goforer.weather.di.module.fragment.weatherinfo.WeatherInfoFragmentModule
import com.goforer.weather.presentation.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityModule {
    @ContributesAndroidInjector(
        modules = [
            HomeFragmentModule::class, WeatherInfoFragmentModule::class
        ]
    )

    abstract fun contributeMainActivity(): MainActivity
}