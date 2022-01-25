package com.ogmaconceptions.androidruntimepermission.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LanguageChange.setLocale(newBase!!))
    }

}