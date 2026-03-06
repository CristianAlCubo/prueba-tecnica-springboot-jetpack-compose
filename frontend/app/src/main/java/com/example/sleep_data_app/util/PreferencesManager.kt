package com.example.sleep_data_app.util

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveUser(userId: String, username: String) {
        prefs.edit().apply {
            putString(KEY_USER_ID, userId)
            putString(KEY_USERNAME, username)
            apply()
        }
    }

    fun getUserId(): String? = prefs.getString(KEY_USER_ID, null)

    fun getUsername(): String? = prefs.getString(KEY_USERNAME, null)

    fun isLoggedIn(): Boolean = getUserId() != null

    fun clearUser() {
        prefs.edit().apply {
            remove(KEY_USER_ID)
            remove(KEY_USERNAME)
            apply()
        }
    }

    companion object {
        private const val PREFS_NAME = "sleep_data_prefs"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USERNAME = "username"

        @Volatile
        private var instance: PreferencesManager? = null

        fun getInstance(context: Context): PreferencesManager {
            return instance ?: synchronized(this) {
                instance ?: PreferencesManager(context.applicationContext).also { instance = it }
            }
        }
    }
}
