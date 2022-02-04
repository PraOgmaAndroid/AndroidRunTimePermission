package com.ogmaconceptions.androidruntimepermission.utils

import android.content.Context
import android.content.res.Configuration
import java.util.*

object LanguageChange {

    lateinit var lang: SharedStorage

    fun setLocale(c: Context, language: String): Context {
        return changeLanguage(language, c)
    }

    fun changeLanguage(languageToChange: String, context: Context): Context {
        val locale = Locale(languageToChange)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        lang = SharedStorage(context)
        lang.language = languageToChange
        return context.createConfigurationContext(config)
    }

}