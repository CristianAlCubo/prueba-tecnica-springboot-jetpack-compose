package com.ccubillos.prueba.repository

import com.ccubillos.prueba.models.SleepData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.UUID

interface ISleepDataRepository : JpaRepository<SleepData, UUID> {
    fun findByUserId(userId: UUID): List<SleepData>
}