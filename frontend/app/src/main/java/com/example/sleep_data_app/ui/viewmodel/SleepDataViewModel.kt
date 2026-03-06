package com.example.sleep_data_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.sleep_data_app.data.model.SleepData
import com.example.sleep_data_app.data.model.SleepDataMetrics
import com.example.sleep_data_app.data.repository.Result
import com.example.sleep_data_app.data.repository.SleepRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SleepDataUiState(
    val isLoading: Boolean = false,
    val metrics: SleepDataMetrics? = null,
    val sleepDataList: List<SleepData> = emptyList(),
    val error: String? = null,
    val saveSuccess: Boolean = false,
    val averageSleepHours: Double = 0.0,
    val averageMood: Double = 0.0
)

class SleepDataViewModel(
    private val repository: SleepRepository,
    private val userId: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(SleepDataUiState())
    val uiState: StateFlow<SleepDataUiState> = _uiState.asStateFlow()

    init {
        loadSleepData()
    }

    fun loadSleepData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            when (val result = repository.getSleepDataMetrics(userId)) {
                is Result.Success -> {
                    val metrics = result.data
                    _uiState.value = SleepDataUiState(
                        isLoading = false,
                        metrics = metrics,
                        sleepDataList = metrics.entries,
                        averageSleepHours = metrics.averageSleepHours,
                        averageMood = metrics.averageMood
                    )
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }

    fun saveSleepData(sleepData: SleepData) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null, saveSuccess = false)

            val dataWithUserId = sleepData.copy(userId = userId)
            when (val result = repository.saveSleepData(dataWithUserId)) {
                is Result.Success -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        saveSuccess = true
                    )
                    loadSleepData()
                }
                is Result.Error -> {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }

    fun resetSaveSuccess() {
        _uiState.value = _uiState.value.copy(saveSuccess = false)
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    class Factory(
        private val repository: SleepRepository,
        private val userId: String
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SleepDataViewModel(repository, userId) as T
        }
    }
}
