package com.goforer.base.utils.time

import android.annotation.SuppressLint
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import java.text.SimpleDateFormat
import java.time.Instant

import java.util.*

object TimeConverter {
    private const val DATE_PATTERN_TIMEZONE = "EEE dd MMM"

    @SuppressLint("SimpleDateFormat")
    fun convertTimestampToDate(timestamp: Long): String = SimpleDateFormat(DATE_PATTERN_TIMEZONE).format(Date.from(Instant.ofEpochSecond(timestamp)))
}