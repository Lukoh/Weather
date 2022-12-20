package com.goforer.fitpettest.data.source.model.entity.home.response

import android.os.Parcelable
import com.goforer.fitpettest.data.source.model.entity.home.BaseEntity
import com.goforer.fitpettest.presentation.ui.home.adapter.CityWeatherAdapter.Companion.VIEW_WEATHER_TYPE
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherInfo(
    var city: String = "",
    val viewType: Int = VIEW_WEATHER_TYPE,
    val clouds: Int = 0,
    val deg: Int = 0,
    val dt: Long = 0,
    val feels_like: FeelsLike? = null,
    val gust: Float = 0.0F,
    val humidity: Int = 0,
    val pop: Float = 0.0F,
    val pressure: Int = 0,
    val rain: Float = 0.0F,
    val speed: Float = 0.0F,
    val sunrise: Int = 0,
    val sunset: Int = 0,
    val temp: Temp? = null,
    val weather: List<Weather>? = null
) : BaseEntity(), Parcelable {
    @Parcelize
    data class FeelsLike(
        val day: Float,
        val eve: Float,
        val morn: Float,
        val night: Float
    ) : BaseEntity(), Parcelable

    @Parcelize
    data class Temp(
        val day: Float,
        val eve: Float,
        val max: Float,
        val min: Float,
        val morn: Float,
        val night: Float
    ) : BaseEntity(), Parcelable

    @Parcelize
    data class Weather(
        val description: String,
        val icon: String,
        val id: Int,
        val main: String
    ) : BaseEntity(), Parcelable
}