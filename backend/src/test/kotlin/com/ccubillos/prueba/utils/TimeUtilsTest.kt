package com.ccubillos.prueba.utils

import org.junit.jupiter.api.Assertions.assertEquals
import kotlin.test.Test

class TimeUtilsTest{

    @Test
    fun `calcula horas netas de sueño correctamente cuando no cruza medianoche`() {
        val bedtime = "22:00"
        val wakeup = "06:00"
        val expectedHours = 8.0

        val result = TimeUtils.calculateNetSleepHours(bedtime, wakeup   )
        assertEquals(expectedHours, result)
    }

    @Test
    fun `calcula horas netas de sueño correctamente cuando cruza medianoche`() {
        val bedtime = "23:30"
        val wakeup = "07:15"
        val expectedHours = 7.75 // 7 horas y 45 minutos

        val result = TimeUtils.calculateNetSleepHours(bedtime, wakeup)
        assertEquals(expectedHours, result)
    }

    @Test
    fun `calcula horas netas de sueño con minutos exactos`() {
        val bedtime = "22:45"
        val wakeup = "05:30"
        val expectedHours = 6.75 // 6 horas y 45 minutos

        val result = TimeUtils.calculateNetSleepHours(bedtime, wakeup)
        assertEquals(expectedHours, result)
    }
}