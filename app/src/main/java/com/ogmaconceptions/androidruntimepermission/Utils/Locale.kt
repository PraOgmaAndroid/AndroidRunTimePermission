package com.ogmaconceptions.androidruntimepermission.Utils

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

class Locale {
    companion object {
        fun changeLanguage(languageToChange: String, context: Context) {
            val locale = Locale(languageToChange)
            Locale.setDefault(locale)
            val config = Configuration()
            config.setLocale(locale)
            context.resources
                .updateConfiguration(config, context.resources.displayMetrics)
        }
    }
}