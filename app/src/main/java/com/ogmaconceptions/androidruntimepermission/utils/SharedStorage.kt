package com.ogmaconceptions.androidruntimepermission.utils

import android.content.Context
import android.content.SharedPreferences

class SharedStorage(val context: Context) {
    private val PREF_NAME = "MultiLingual"
    private lateinit var sharedPref: SharedPreferences

    var language: String
        get() {
            sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            return sharedPref.getString(PREF_NAME, "en") as String
        }
        set(language) {
            sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString(PREF_NAME, language)
            editor.apply()
        }

}