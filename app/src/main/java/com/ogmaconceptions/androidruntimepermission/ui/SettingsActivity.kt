package com.ogmaconceptions.androidruntimepermission.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ogmaconceptions.androidruntimepermission.R
import com.ogmaconceptions.androidruntimepermission.Utils.Locale
import com.ogmaconceptions.androidruntimepermission.Utils.SharedStorage
import com.ogmaconceptions.androidruntimepermission.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var settingsBinding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        settingsBinding = ActivitySettingsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(settingsBinding.root)

        settingsBinding.topAppBar.setNavigationOnClickListener {
            finish()
        }

        if (SharedStorage.getStoredLanguage(this) == "en") {
            Locale.changeLanguage("en", this)
            settingsBinding.btnEnglish.isChecked = true
            settingsBinding.materialText.text = resources.getString(R.string.language)
        } else {
            Locale.changeLanguage("bn", this)
            settingsBinding.btnBengali.isChecked = true
            settingsBinding.materialText.text = resources.getString(R.string.language)
        }

        settingsBinding.btgLanguage.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btnEnglish -> {
                        Locale.changeLanguage("en", this)
                        SharedStorage.storeLanguage(this, "en")
                        Intent(this, SettingsActivity::class.java).also {
                            finish()
                            overridePendingTransition(0, 0)
                            startActivity(it)
                            overridePendingTransition(0, 0)
                        }
                    }
                    else -> {
                        Locale.changeLanguage("bn", this)
                        SharedStorage.storeLanguage(this, "bn")
                        Intent(this, SettingsActivity::class.java).also {
                            finish()
                            overridePendingTransition(0, 0)
                            startActivity(it)
                            overridePendingTransition(0, 0)
                        }
                    }
                }
            }
        }
    }
}