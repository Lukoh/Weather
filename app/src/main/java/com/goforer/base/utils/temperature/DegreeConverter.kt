package com.goforer.base.utils.temperature

object DegreeConverter {
    private const val KELVIN_ABS = 273.15

    fun covertCelsius(fahrenheit: Float): String  {
        val celsius = (fahrenheit - KELVIN_ABS).toFloat()

        return String.format("%.2f", celsius)
    }
}