package com.kjh.dietmanagement.view.common

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences("pref", Context.MODE_PRIVATE)

    fun getString(key: String): String {
        return preferences.getString(key, null).toString()
    }

    fun setString(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }
}