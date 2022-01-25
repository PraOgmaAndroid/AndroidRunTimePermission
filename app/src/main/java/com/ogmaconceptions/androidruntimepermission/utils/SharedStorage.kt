package com.ogmaconceptions.androidruntimepermission.utils

import android.content.Context
import android.content.SharedPreferences

object SharedStorage {
    private const val PREF_NAME = "MultiLingual"
    private lateinit var sharedPref: SharedPreferences

    fun storeLanguage(context: Context, language: String) {
        sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString(PREF_NAME, language)
        editor.apply()
    }

    fun getStoredLanguage(context: Context): String? {
        sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPref.getString(PREF_NAME, "en")
    }

}