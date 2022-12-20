package com.goforer.fitpettest

import com.goforer.base.utils.temperature.DegreeConverter
import org.junit.Test
import org.junit.Assert.*

class CovertCelsiusUnitTest {
    @Test
    fun covertToCelsius() {
        val celsius = DegreeConverter.covertCelsius (270.55F)

        assertEquals(celsius, "-2.60")
    }
}