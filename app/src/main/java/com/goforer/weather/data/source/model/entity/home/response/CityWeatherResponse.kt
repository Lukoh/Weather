package com.goforer.weather.data.source.model.entity.home.response

import android.os.Parcelable
import com.goforer.weather.data.source.model.entity.home.BaseEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class CityWeatherResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: MutableList<WeatherInfo>,
    val message: Double
) : BaseEntity(), Parcelable