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
    private const val DATE_PATTERN = "EEE dd MMM"
    private const val DATE_TIME_PATTERN_ = "EEE dd MMM HH:mm:ss"

    @SuppressLint("SimpleDateFormat")
    fun convertTimestampToDate(timestamp: Long): String = SimpleDateFormat(DATE_PATTERN).format(Date.from(Instant.ofEpochSecond(timestamp)))

    @SuppressLint("SimpleDateFormat")
    fun convertTimestampToDateTime(timestamp: Long): String = SimpleDateFormat(DATE_TIME_PATTERN_).format(Date.from(Instant.ofEpochSecond(timestamp)))
}