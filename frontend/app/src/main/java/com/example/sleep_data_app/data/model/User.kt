package com.example.sleep_data_app.data.model

data class User(
    val id: String,
    val username: String
)

data class CreateUserRequest(
    val username: String
)
