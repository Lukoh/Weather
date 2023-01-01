package com.goforer.weather

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.goforer.base.utils.time.TimeConverter
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConvertTimestampToDateTimeInstrumentTest {
    @Test
    fun convertTimestampToDateTime() {
        val dateTime = TimeConverter.convertTimestampToDateTime(1672561411)

        assertEquals(dateTime, "Sun 01 Jan 17:23:31")
    }
}