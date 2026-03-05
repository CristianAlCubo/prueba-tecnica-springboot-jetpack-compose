package com.ccubillos.prueba.utils

import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class TimeUtils {

    companion object {
        private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")

        /**
         * Calcula las horas netas de sueño a partir de bedtime y wakeupTime en formato "HH:mm"
         * Retorna el valor en horas como Double (por ejemplo 8.25)
         */
        fun calculateNetSleepHours(bedtime: String, wakeupTime: String): Double {
            val bed = LocalTime.parse(bedtime, formatter)
            val wake = LocalTime.parse(wakeupTime, formatter)

            var durationMinutes = Duration.between(bed, wake).toMinutes()
            if (durationMinutes < 0) {
                // Si es negativo, significa que wakeup es al día siguiente
                durationMinutes += 24 * 60
            }

            return durationMinutes.toDouble() / 60.0
        }
    }
}