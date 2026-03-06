package com.example.sleep_data_app.data.repository

import com.example.sleep_data_app.data.api.NetworkModule
import com.example.sleep_data_app.data.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}

class SleepRepository {

    private val apiService = NetworkModule.apiService

    suspend fun createUser(username: String): Result<User> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.createUser(CreateUserRequest(username))
            if (response.isSuccessful && response.body()?.data != null) {
                Result.Success(response.body()!!.data!!)
            } else {
                Result.Error(response.body()?.error ?: "Error creating user")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Network error")
        }
    }

    suspend fun getUserByUsername(username: String): Result<User> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getUserByUsername(username)
            if (response.isSuccessful && response.body()?.data != null) {
                Result.Success(response.body()!!.data!!)
            } else {
                Result.Error("User not found")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Network error")
        }
    }

    suspend fun saveSleepData(sleepData: SleepData): Result<SleepData> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.saveSleepData(sleepData)
            if (response.isSuccessful && response.body()?.data != null) {
                Result.Success(response.body()!!.data!!)
            } else {
                Result.Error(response.body()?.error ?: "Error saving sleep data")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Network error")
        }
    }

    suspend fun getSleepDataMetrics(userId: String): Result<SleepDataMetrics> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.getSleepDataMetrics(userId)
            if (response.isSuccessful && response.body()?.data != null) {
                Result.Success(response.body()!!.data!!)
            } else {
                Result.Error("Error fetching metrics")
            }
        } catch (e: Exception) {
            Result.Error(e.message ?: "Network error")
        }
    }

    companion object {
        @Volatile
        private var instance: SleepRepository? = null

        fun getInstance(): SleepRepository {
            return instance ?: synchronized(this) {
                instance ?: SleepRepository().also { instance = it }
            }
        }
    }
}
