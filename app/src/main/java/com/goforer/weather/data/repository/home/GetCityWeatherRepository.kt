package com.goforer.weather.data.repository.home

import com.goforer.weather.data.Query
import com.goforer.weather.data.repository.Repository
import com.goforer.weather.data.source.model.entity.home.response.CityWeatherResponse
import com.goforer.weather.data.source.network.mediator.DataMediator
import com.goforer.weather.data.source.network.response.Resource
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCityWeatherRepository
@Inject
constructor() : Repository<Resource>() {
    override fun handle(viewModelScope: CoroutineScope, query: Query) = object :
        DataMediator<CityWeatherResponse>(viewModelScope, false) {
        override fun load() = restAPI.getCityWeather(query.args[0] as String, query.args[1] as Int, API_KEY)
    }.asSharedFlow
}