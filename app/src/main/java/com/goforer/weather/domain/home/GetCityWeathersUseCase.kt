package com.goforer.weather.domain.home

import com.goforer.weather.data.repository.home.GetCityWeatherRepository
import com.goforer.weather.domain.RepoUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCityWeatherUseCase
@Inject
constructor(override val repository: GetCityWeatherRepository) : RepoUseCase(repository)