package com.example.noteapp.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper {

    private lateinit var sharedPref: SharedPreferences

    fun unit(context: Context){
        sharedPref = context.getSharedPreferences("shared", Context.MODE_PRIVATE)
    }
    var text: Boolean
        get() = sharedPref.getBoolean("text", false)
        set(value) = sharedPref.edit().putBoolean("text", value).apply()

    fun setOnBoardingCompleted(b: Boolean) {
        sharedPref.edit().putBoolean("onBoardingCompleted", text).apply()
    }
    fun isOnBoardingCompleted(b: Boolean): Boolean {
        return sharedPref.getBoolean("onBoardingCompleted", true)
    }
    fun isLinearLayout(): Boolean {
        return sharedPref.getBoolean("isLinearLayout", true)    }

    fun setLinearLayout(isLinearLayout: Boolean) {
        sharedPref.edit().putBoolean("isLinearLayout", isLinearLayout).apply()
    }
    fun setRegistered(isRegistered: Boolean){
        sharedPref.edit().putBoolean("isRegistered", isRegistered).apply()
    }
    fun isRegistered(): Boolean{
        return sharedPref.getBoolean("isRegistered", false)

    }
}