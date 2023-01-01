package com.goforer.weather

import com.goforer.base.utils.time.TimeConverter
import org.junit.Assert
import org.junit.Test

class ConvertTimestampToDateUnitTest {
    @Test
    fun convertTimestampToDate() {
        val date = TimeConverter.convertTimestampToDate(1661857200)

        Assert.assertEquals(date, "Tue 30 Aug")
    }
}