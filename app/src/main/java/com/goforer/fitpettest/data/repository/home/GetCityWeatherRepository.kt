package com.goforer.fitpettest.data.repository.home

import com.goforer.fitpettest.data.Query
import com.goforer.fitpettest.data.repository.Repository
import com.goforer.fitpettest.data.source.model.entity.home.response.CityWeatherResponse
import com.goforer.fitpettest.data.source.network.mediator.DataMediator
import com.goforer.fitpettest.data.source.network.response.Resource
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCityWeatherRepository
@Inject
constructor() : Repository<Resource>() {
    override fun handle(viewModelScope: CoroutineScope, query: Query) = object :
        DataMediator<CityWeatherResponse>(viewModelScope, false) {
        override fun load() = restAPI.getCityWeather(query.args[0] as String, ITEM_COUNT, API_KEY)
    }.asSharedFlow
}