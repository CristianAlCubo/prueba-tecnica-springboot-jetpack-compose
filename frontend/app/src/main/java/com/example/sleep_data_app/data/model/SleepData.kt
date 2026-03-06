package com.example.sleep_data_app.data.model

data class SleepData(
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
)

data class SleepDataMetrics(
    val averageSleepHours: Double,
    val averageMood: Double,
    val entries: List<SleepData>
)
