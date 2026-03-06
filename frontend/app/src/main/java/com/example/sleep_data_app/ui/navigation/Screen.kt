package com.example.sleep_data_app.ui.navigation

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Home : Screen("home")
    data object AddSleepData : Screen("add_sleep_data")
    data object SleepDetail : Screen("sleep_detail/{sleepDataId}") {
        fun createRoute(sleepDataId: Long) = "sleep_detail/$sleepDataId"
    }
}
