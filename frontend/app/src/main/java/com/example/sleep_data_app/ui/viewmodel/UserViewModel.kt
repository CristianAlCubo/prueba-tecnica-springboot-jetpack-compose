package com.example.sleep_data_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.sleep_data_app.data.model.User
import com.example.sleep_data_app.data.repository.Result
import com.example.sleep_data_app.data.repository.SleepRepository
import com.example.sleep_data_app.util.PreferencesManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UserUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null,
    val isLoggedIn: Boolean = false
)

class UserViewModel(
    private val repository: SleepRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    init {
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        if (preferencesManager.isLoggedIn()) {
            _uiState.value = UserUiState(
                isLoggedIn = true,
                user = User(
                    id = preferencesManager.getUserId() ?: "",
                    username = preferencesManager.getUsername() ?: ""
                )
            )
        }
    }

    fun loginOrCreateUser(username: String) {
        if (username.isBlank()) {
            _uiState.value = _uiState.value.copy(error = "Username cannot be empty")
            return
        }

        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null)

            val result = repository.getUserByUsername(username)
            val user = when (result) {
                is Result.Success -> result.data
                is Result.Error -> {
                    val createResult = repository.createUser(username)
                    when (createResult) {
                        is Result.Success -> createResult.data
                        is Result.Error -> null
                    }
                }
            }

            if (user != null) {
                preferencesManager.saveUser(user.id, user.username)
                _uiState.value = UserUiState(
                    isLoggedIn = true,
                    user = user
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Failed to create or find user"
                )
            }
        }
    }

    fun logout() {
        preferencesManager.clearUser()
        _uiState.value = UserUiState()
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }

    class Factory(
        private val repository: SleepRepository,
        private val preferencesManager: PreferencesManager
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return UserViewModel(repository, preferencesManager) as T
        }
    }
}
