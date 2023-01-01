package com.goforer.weather

import com.goforer.base.utils.time.TimeConverter
import org.junit.Assert
import org.junit.Test

class ConvertTimestampToDateTimeUnitTest {
    @Test
    fun convertTimestampToDateTime() {
        val dateTime = TimeConverter.convertTimestampToDateTime(1672561411)

        Assert.assertEquals(dateTime, "Sun 01 Jan 17:23:31")
    }
}