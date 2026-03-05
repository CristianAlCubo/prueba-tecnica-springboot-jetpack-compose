package com.ccubillos.prueba.controllers

import com.ccubillos.prueba.dto.ApiResponseDTO
import com.ccubillos.prueba.dto.sleep_data.SleepDataDTO
import com.ccubillos.prueba.dto.sleep_data.SleepDataMetricsDTO
import com.ccubillos.prueba.models.SleepData
import com.ccubillos.prueba.services.SleepDataService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/sleep-data")
class SleepDataController(
    private val sleepDataMetricsService: SleepDataService
) {

    @GetMapping
    fun getSleepData(): ResponseEntity<ApiResponseDTO<List<SleepDataDTO>>> =
        ResponseEntity.ok(
            ApiResponseDTO(
                status = HttpStatus.OK,
                data = sleepDataMetricsService.getAllSleepData().map { SleepDataDTO(it) }
            )
        )

    @GetMapping("/{userId}")
    fun getSleepDataMetrics(@PathVariable userId: String): ResponseEntity<ApiResponseDTO<SleepDataMetricsDTO>> =
        ResponseEntity.ok(
            ApiResponseDTO(
                status = HttpStatus.OK,
                data = sleepDataMetricsService.getSleepDataMetricsByUserId(userId)
            )
        )

    @PostMapping
    fun saveSleepData(@RequestBody sleepData: SleepDataDTO): ResponseEntity<ApiResponseDTO<SleepDataDTO>> =
        ResponseEntity.ok(
            ApiResponseDTO<SleepDataDTO>(
                status = HttpStatus.OK,
                data = SleepDataDTO(sleepDataMetricsService.saveSleepData(sleepData))
            )
        )
}