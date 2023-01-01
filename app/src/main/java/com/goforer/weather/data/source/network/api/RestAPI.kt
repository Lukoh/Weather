package com.goforer.weather.data.source.network.api

import com.goforer.weather.data.source.model.entity.home.response.CityWeatherResponse
import com.goforer.weather.data.source.network.response.ApiResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query

interface RestAPI {
    @GET("/data/2.5/forecast/daily")
    fun getCityWeather(
        @Query("q") city: String,
        @Query("cnt") count: Int,
        @Query("appid") appid: String,
    ): Flow<ApiResponse<CityWeatherResponse>>
}