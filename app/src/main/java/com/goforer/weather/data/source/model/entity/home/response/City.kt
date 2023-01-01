package com.goforer.weather.data.source.model.entity.home.response

import android.os.Parcelable
import com.goforer.weather.data.source.model.entity.home.BaseEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class City(
    val coord: Coord,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val timezone: Int
) : BaseEntity(), Parcelable {
    @Parcelize
    data class Coord(
        val lat: Double,
        val lon: Double
    ) : BaseEntity(), Parcelable
}