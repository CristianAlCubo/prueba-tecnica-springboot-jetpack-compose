package com.example.sleep_data_app.data.api

import com.example.sleep_data_app.data.model.*
import retrofit2.Response
import retrofit2.http.*

interface SleepApiService {

    @POST("api/user")
    suspend fun createUser(@Body request: CreateUserRequest): Response<ApiResponse<User>>

    @GET("api/user/{username}")
    suspend fun getUserByUsername(@Path("username") username: String): Response<ApiResponse<User>>

    @GET("api/user")
    suspend fun getAllUsers(): Response<ApiResponse<List<User>>>

    @POST("api/sleep-data")
    suspend fun saveSleepData(@Body sleepData: SleepData): Response<ApiResponse<SleepData>>

    @GET("api/sleep-data/{userId}")
    suspend fun getSleepDataMetrics(@Path("userId") userId: String): Response<ApiResponse<SleepDataMetrics>>

    @GET("api/sleep-data")
    suspend fun getAllSleepData(): Response<ApiResponse<List<SleepData>>>
}
