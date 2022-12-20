package com.goforer.fitpettest

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.goforer.base.utils.time.TimeConverter
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConvertTimestampToDateInstrumentTest {
    @Test
    fun convertTimestampToDate() {
        val date = TimeConverter.convertTimestampToDate(1661857200)

        assertEquals(date, "Tue 30 Aug")
    }
}