package com.ccubillos.prueba.dto.sleep_data

import com.ccubillos.prueba.models.SleepData

data class SleepDataDTO(
    val id: Long? = null,
    val userId: String,
    val bedtime: String,
    val wakeupTime: String,
    val mood: Int,
    val energy: Int,
    val focus: Int,
    val dreamed: Boolean,
    val wokeUpAtNight: Boolean,
    val restedLevel: Int,
    val stressLevel: Int,
    val caffeineAfter6pm: Boolean,
    val comment: String? = null
) {
    constructor(model: SleepData) : this(
        id = model.id,
        userId = model.user.id.toString(),
        bedtime = model.bedtime,
        wakeupTime = model.wakeupTime,
        mood = model.mood,
        energy = model.energy,
        focus = model.focus,
        dreamed = model.dreamed,
        wokeUpAtNight = model.wokeUpAtNight,
        restedLevel = model.restedLevel,
        stressLevel = model.stressLevel,
        caffeineAfter6pm = model.caffeineAfter6pm,
        comment = model.comment
    )
}