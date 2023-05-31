package com.example.befresh.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPref(context: Context) {
    private val sharedpreferences: SharedPreferences

    companion object {
        lateinit var instance: SharedPref
            private set
    }

    init {
        instance = this
        sharedpreferences = context.getSharedPreferences(PrefConstants.PREFERENCE_NAME.value, Context.MODE_PRIVATE)
    }

    fun putString(name: String?, value: String?) {
        sharedpreferences.edit().putString(name, value).apply()
    }

    fun getString(name: String?): String? {
        return sharedpreferences.getString(name, "")
    }

    fun setBoolean(name: String?, value: Boolean?) {
        sharedpreferences.edit().putBoolean(name, value!!).apply()
    }

    fun getBoolean(name: String?): Boolean? {
        return sharedpreferences.getBoolean(name, false)
    }

    fun setInt(name: String, value: Int?) {
        sharedpreferences.edit().putInt(name, value!!).apply()
    }
    fun getInt(name: String): Int? {
        return sharedpreferences.getInt(name, 0)
    }
    fun clearAllPreferences() {
        sharedpreferences.edit().clear().apply()
    }
}