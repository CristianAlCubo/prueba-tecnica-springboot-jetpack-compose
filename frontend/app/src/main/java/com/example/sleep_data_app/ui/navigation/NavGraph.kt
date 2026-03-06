package com.example.sleep_data_app.ui.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.sleep_data_app.data.model.SleepData
import com.example.sleep_data_app.data.repository.SleepRepository
import com.example.sleep_data_app.ui.screens.*
import com.example.sleep_data_app.ui.viewmodel.SleepDataViewModel
import com.example.sleep_data_app.ui.viewmodel.UserViewModel
import com.example.sleep_data_app.util.PreferencesManager

@SuppressLint("UnrememberedGetBackStackEntry")
@Composable
fun NavGraph(
    navController: NavHostController,
    userViewModel: UserViewModel,
    modifier: Modifier = Modifier
) {
    val userUiState by userViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager.getInstance(context) }
    val repository = remember { SleepRepository.getInstance() }
    var pendingHomeSnackbarMessage by remember { mutableStateOf<String?>(null) }

    val startDestination = if (userUiState.isLoggedIn) "home" else "login"

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable("login") {
            var loginUsername by remember { mutableStateOf(userUiState.user?.username ?: "") }

            LaunchedEffect(userUiState.user?.username) {
                loginUsername = userUiState.user?.username ?: loginUsername
            }

            LoginScreen(
                username = loginUsername,
                onUsernameChange = { loginUsername = it },
                onLoginClick = {
                    userViewModel.loginOrCreateUser(loginUsername)
                },
                isLoading = userUiState.isLoading,
                error = userUiState.error
            )
        }

        composable("home") {
            val userId = preferencesManager.getUserId() ?: return@composable
            val sleepDataViewModel: SleepDataViewModel = viewModel(
                factory = SleepDataViewModel.Factory(repository, userId)
            )
            val sleepUiState by sleepDataViewModel.uiState.collectAsState()

            HomeScreen(
                username = preferencesManager.getUsername() ?: "",
                averageSleepHours = sleepUiState.averageSleepHours,
                averageMood = sleepUiState.averageMood,
                sleepDataList = sleepUiState.sleepDataList,
                isLoading = sleepUiState.isLoading,
                snackbarMessage = pendingHomeSnackbarMessage,
                onSnackbarMessageShown = { pendingHomeSnackbarMessage = null },
                onAddMeasurementClick = {
                    navController.navigate("add_sleep_data")
                },
                onLogoutClick = {
                    userViewModel.logout()
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                onSleepDataClick = { sleepData ->
                    navController.navigate("sleep_detail/${sleepData.id}")
                },
                onRefresh = { sleepDataViewModel.loadSleepData() }
            )
        }

        composable("add_sleep_data") {
            val userId = preferencesManager.getUserId() ?: return@composable
            val homeBackStackEntry = remember {
                navController.getBackStackEntry("home")
            }
            val sleepDataViewModel: SleepDataViewModel = viewModel(
                viewModelStoreOwner = homeBackStackEntry,
                factory = SleepDataViewModel.Factory(repository, userId)
            )
            val sleepUiState by sleepDataViewModel.uiState.collectAsState()

            AddSleepDataScreen(
                onBackClick = { navController.popBackStack() },
                onSave = { sleepData ->
                    sleepDataViewModel.saveSleepData(sleepData)
                },
                isLoading = sleepUiState.isLoading,
                error = sleepUiState.error,
                saveSuccess = sleepUiState.saveSuccess,
                onSaveSuccessShown = { sleepDataViewModel.resetSaveSuccess() },
                onSaveNavigateBack = {
                    pendingHomeSnackbarMessage = "Datos guardados con éxito"
                    navController.popBackStack()
                },
                onErrorShown = { sleepDataViewModel.clearError() }
            )
        }

        composable(
            route = "sleep_detail/{sleepDataId}",
            arguments = listOf(
                navArgument("sleepDataId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val sleepDataId = backStackEntry.arguments?.getLong("sleepDataId") ?: return@composable
            val userId = preferencesManager.getUserId() ?: return@composable
            val sleepDataViewModel: SleepDataViewModel = viewModel(
                factory = SleepDataViewModel.Factory(repository, userId)
            )
            val sleepUiState by sleepDataViewModel.uiState.collectAsState()

            val sleepData = sleepUiState.sleepDataList.find { it.id == sleepDataId }

            if (sleepData != null) {
                SleepDetailScreen(
                    sleepData = sleepData,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}
