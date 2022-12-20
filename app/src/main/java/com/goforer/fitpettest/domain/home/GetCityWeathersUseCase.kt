package com.goforer.fitpettest.domain.home

import com.goforer.fitpettest.data.repository.home.GetCityWeatherRepository
import com.goforer.fitpettest.domain.RepoUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCityWeatherUseCase
@Inject
constructor(override val repository: GetCityWeatherRepository) : RepoUseCase(repository)