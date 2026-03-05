package com.ccubillos.prueba.models

import jakarta.persistence.Entity
import jakarta.persistence.Table

import jakarta.persistence.*

@Entity
@Table(name = "sleep_data")
class SleepData(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var userId: String,

    @Column(nullable = false)
    var bedtime: String, // "HH:mm"

    @Column(nullable = false)
    var wakeupTime: String, // "HH:mm"

    @Column(nullable = false)
    var mood: Int, // 1-10

    @Column(nullable = false)
    var energy: Int, // 1-10

    @Column(nullable = false)
    var focus: Int, // 1-10

    @Column(nullable = false)
    var dreamed: Boolean,

    @Column(nullable = false)
    var wokeUpAtNight: Boolean,

    @Column(nullable = false)
    var restedLevel: Int, // 1-10

    @Column(nullable = false)
    var stressLevel: Int, // 1-10

    @Column(nullable = false)
    var caffeineAfter6pm: Boolean,

    @Column(nullable = true, length = 500)
    var comment: String? = null

) {
    constructor() : this(
        userId = "",
        bedtime = "00:00",
        wakeupTime = "00:00",
        mood = 1,
        energy = 1,
        focus = 1,
        dreamed = false,
        wokeUpAtNight = false,
        restedLevel = 1,
        stressLevel = 1,
        caffeineAfter6pm = false,
        comment = null
    )
}