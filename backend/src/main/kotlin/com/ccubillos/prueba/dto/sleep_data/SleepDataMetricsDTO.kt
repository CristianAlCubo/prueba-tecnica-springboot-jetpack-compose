package com.ccubillos.prueba.dto.sleep_data

class SleepDataMetricsDTO(
   var averageSleepHours : Double,
    var averageMood : Double,
    var entries : List<SleepDataDTO>
) {}