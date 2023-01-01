package com.goforer.weather.presentation.event.home

import com.goforer.weather.data.source.model.entity.home.response.WeatherInfo
import com.goforer.weather.presentation.EventBus
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherInfoEventBus
@Inject
constructor() : EventBus<WeatherInfo>()