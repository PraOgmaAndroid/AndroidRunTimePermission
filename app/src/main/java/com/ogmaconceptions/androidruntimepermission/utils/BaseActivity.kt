package com.ogmaconceptions.androidruntimepermission.utils

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    lateinit var storedLang: SharedStorage

    override fun attachBaseContext(newBase: Context?) {
        storedLang = SharedStorage(newBase!!)
        super.attachBaseContext(LanguageChange.setLocale(newBase, storedLang.language))
    }

}