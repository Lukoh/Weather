package com.goforer.weather.data.source.model.entity.home.response.home

import android.os.Parcelable
import com.goforer.weather.data.source.model.entity.home.BaseEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class CityWeatherResponse(
    val base: String,
    val clouds: Clouds,
    val cod: Int,
    val coord: Coord,
    val dt: Int,
    val id: Int,
    val main: Main,
    val name: String,
    val rain: Rain,
    val sys: Sys,
    val timezone: Int,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
) : BaseEntity(), Parcelable {
    @Parcelize
    data class Clouds(
        val all: Int
    ) : BaseEntity(), Parcelable

    @Parcelize
    data class Coord(
        val lat: Double,
        val lon: Double
    ) : BaseEntity(), Parcelable

    @Parcelize
    data class Main(
        val feels_like: Double,
        val grnd_level: Int,
        val humidity: Int,
        val pressure: Int,
        val sea_level: Int,
        val temp: Double,
        val temp_max: Double,
        val temp_min: Double
    ) : BaseEntity(), Parcelable

    @Parcelize
    data class Rain(
        val `1h`: Double
    ) : BaseEntity(), Parcelable

    @Parcelize
    data class Sys(
        val country: String,
        val id: Int,
        val sunrise: Int,
        val sunset: Int,
        val type: Int
    ) : BaseEntity(), Parcelable

    @Parcelize
    data class Weather(
        val description: String,
        val icon: String,
        val id: Int,
        val main: String
    ) : BaseEntity(), Parcelable

    @Parcelize
    data class Wind(
        val deg: Int,
        val gust: Double,
        val speed: Double
    ) : BaseEntity(), Parcelable
}