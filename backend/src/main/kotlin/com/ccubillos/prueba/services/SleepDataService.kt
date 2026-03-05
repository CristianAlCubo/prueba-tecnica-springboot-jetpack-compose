package com.ccubillos.prueba.services

import com.ccubillos.prueba.dto.sleep_data.SleepDataDTO
import com.ccubillos.prueba.dto.sleep_data.SleepDataMetricsDTO
import com.ccubillos.prueba.models.SleepData
import com.ccubillos.prueba.repository.ISleepDataRepository
import com.ccubillos.prueba.repository.IUserRepository
import com.ccubillos.prueba.utils.TimeUtils
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class SleepDataService(
    private val sleepDataRepository: ISleepDataRepository,
    private val userRepository: IUserRepository
) {
    fun getAllSleepData(): List<SleepData> {
        return sleepDataRepository.findAll()
    }

    fun getSleepDataMetricsByUserId(userId: String): SleepDataMetricsDTO {
        val entries = sleepDataRepository.findByUserId(UUID.fromString(userId))

        // Si no hay entradas, promedios son 0.
        if(entries.isEmpty()){
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

    fun saveSleepData(dto: SleepDataDTO): SleepData {

        val user = userRepository.findById(UUID.fromString(dto.userId))
        val entity = SleepData()

        entity.user = user.get()
        entity.bedtime = dto.bedtime
        entity.wakeupTime = dto.wakeupTime
        entity.mood = dto.mood
        entity.energy = dto.energy
        entity.focus = dto.focus
        entity.dreamed = dto.dreamed
        entity.wokeUpAtNight = dto.wokeUpAtNight
        entity.restedLevel = dto.restedLevel
        entity.stressLevel = dto.stressLevel
        entity.caffeineAfter6pm = dto.caffeineAfter6pm
        entity.comment = dto.comment

        return sleepDataRepository.save(entity)
    }
}