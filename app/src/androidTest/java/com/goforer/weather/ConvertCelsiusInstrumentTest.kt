package com.goforer.weather

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.goforer.base.utils.temperature.DegreeConverter

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConvertCelsiusInstrumentTest {
    @Test
    fun covertToCelsius() {
        val celsius = DegreeConverter.covertCelsius (270.55F)

        assertEquals(celsius, "-2.60")
    }
}