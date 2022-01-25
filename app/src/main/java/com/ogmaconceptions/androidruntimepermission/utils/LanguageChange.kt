package com.ogmaconceptions.androidruntimepermission.utils

import android.content.Context
import android.content.res.Configuration
import java.util.*

object LanguageChange {

    fun setLocale(c: Context): Context {
        return changeLanguage(SharedStorage.getStoredLanguage(c)!!, c)
    }

    fun changeLanguage(languageToChange: String, context: Context): Context {
        val locale = Locale(languageToChange)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        return context.createConfigurationContext(config)
    }

}