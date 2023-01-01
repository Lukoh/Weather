package com.goforer.weather.data.source.model.entity.home.local.home

import android.os.Parcelable
import com.goforer.weather.data.source.model.entity.home.BaseEntity
import com.goforer.weather.presentation.stateholders.MediatorViewModel
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class WeathersState(
    val viewModel: @RawValue MediatorViewModel,
    val keyword: String,
    val firstVisiblePosition: Int,
    val lastVisibleItemPos: Int,
    val pickedPosition: Int
) : BaseEntity(), Parcelable