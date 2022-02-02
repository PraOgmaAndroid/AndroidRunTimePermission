package com.ogmaconceptions.androidruntimepermission.utils

import android.content.Context
import android.content.res.Configuration
import java.util.*

object LanguageChange {

    fun setLocale(c: Context, language: String): Context {
        return changeLanguage(language, c)
    }

    fun changeLanguage(languageToChange: String, context: Context): Context {
        val locale = Locale(languageToChange)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        SharedStorage.storeLanguage(context, languageToChange)
        return context.createConfigurationContext(config)
    }

}