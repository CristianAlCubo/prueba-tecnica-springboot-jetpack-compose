package com.example.sleep_data_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.sleep_data_app.data.repository.SleepRepository
import com.example.sleep_data_app.ui.navigation.NavGraph
import com.example.sleep_data_app.ui.theme.SleepdataappTheme
import com.example.sleep_data_app.ui.viewmodel.UserViewModel
import com.example.sleep_data_app.util.PreferencesManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SleepdataappTheme {
                SleepDataApp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun SleepDataApp(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager.getInstance(context) }
    val repository = remember { SleepRepository.getInstance() }

    val userViewModel: UserViewModel = viewModel(
        factory = UserViewModel.Factory(repository, preferencesManager)
    )

    val navController = rememberNavController()

    NavGraph(
        navController = navController,
        userViewModel = userViewModel,
        modifier = modifier
    )
}
