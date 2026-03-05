package com.ccubillos.prueba.models

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "users")
class User(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID?,
    var username: String,

    @OneToMany(
        mappedBy = "user",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.LAZY
        )
    var sleepDataList: MutableList<SleepData> = mutableListOf()

){
    constructor(username : String) : this(null, username)

    fun addSleepData(sleepData: SleepData) {
        sleepData.user = this
        sleepDataList.add(sleepData)
    }

    fun removeSleepData(sleepData: SleepData) {
        sleepDataList.remove(sleepData)
    }
}