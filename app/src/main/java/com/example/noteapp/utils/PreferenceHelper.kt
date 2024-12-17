package com.example.noteapp.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper {

    private lateinit var sharedPref: SharedPreferences

    fun unit(context: Context){
        sharedPref = context.getSharedPreferences("shared", Context.MODE_PRIVATE)
    }

    var text: String?
            get() = sharedPref.getString("text", "")
        set(value) = sharedPref.edit().putString("text", value)!!.apply()

    var isOnBoardShown: Boolean
        get() = sharedPref.getBoolean("isShown", false)
        set(value) = sharedPref.edit().putBoolean("isShown", value).apply()

}