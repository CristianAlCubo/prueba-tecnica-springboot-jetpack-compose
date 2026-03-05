package com.ccubillos.prueba.controllers

import com.ccubillos.prueba.dto.ApiResponseDTO
import com.ccubillos.prueba.dto.SleepDataMetricsDTO
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
    fun getSleepData(): ResponseEntity<ApiResponseDTO<List<SleepData>>> =
        ResponseEntity.ok(
            ApiResponseDTO(
                status = HttpStatus.OK,
                data = sleepDataMetricsService.getAllSleepData()
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
    fun saveSleepData(@RequestBody sleepData: SleepData): ResponseEntity<ApiResponseDTO<SleepData>> =
        ResponseEntity.ok(
            ApiResponseDTO(
                status = HttpStatus.OK,
                data = sleepDataMetricsService.saveSleepData(sleepData)
            )
        )
}