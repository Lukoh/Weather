package com.goforer.weather.di.module.fragment.weatherinfo

import com.goforer.weather.presentation.ui.weather.WeatherInfoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class WeatherInfoFragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeWeatherInfoFragment(): WeatherInfoFragment
}