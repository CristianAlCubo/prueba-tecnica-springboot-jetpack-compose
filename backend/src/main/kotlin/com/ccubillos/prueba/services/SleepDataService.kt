package com.ccubillos.prueba.services

import com.ccubillos.prueba.dto.SleepDataDTO
import com.ccubillos.prueba.dto.SleepDataMetricsDTO
import com.ccubillos.prueba.models.SleepData
import com.ccubillos.prueba.repository.ISleepDataRepository
import com.ccubillos.prueba.utils.TimeUtils
import org.springframework.stereotype.Service

@Service
class SleepDataService(
    private val sleepDataRepository: ISleepDataRepository
) {
    fun getAllSleepData(): List<SleepData> {
        return sleepDataRepository.findAll()
    }

    fun getSleepDataMetricsByUserId(userId: String): SleepDataMetricsDTO {
        val entries = sleepDataRepository.findByUserId(userId)

        // Si no hay entradas, promedios son 0.
        if(entries.isNullOrEmpty()){
            return SleepDataMetricsDTO(
                averageSleepHours = 0.0,
                averageMood = 0.0,
                entries = ArrayList<SleepDataDTO>() // Empty
            );
        }

        return SleepDataMetricsDTO(
            averageSleepHours =  entries.map { TimeUtils.calculateNetSleepHours(it.bedtime, it.wakeupTime) }.average(),
            averageMood = entries.map { it.mood }.average(),
            entries = entries.map { SleepDataDTO(it) }
        )
    }

    fun saveSleepData(sleepData: SleepData): SleepData {
        return sleepDataRepository.save(sleepData)
    }
}