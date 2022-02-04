package com.ogmaconceptions.androidruntimepermission.ui


import android.content.Intent
import android.os.Bundle
import com.ogmaconceptions.androidruntimepermission.R
import com.ogmaconceptions.androidruntimepermission.databinding.ActivitySettingsBinding
import com.ogmaconceptions.androidruntimepermission.utils.BaseActivity
import com.ogmaconceptions.androidruntimepermission.utils.LanguageChange
import com.ogmaconceptions.androidruntimepermission.utils.SharedStorage


class SettingsActivity : BaseActivity() {

    private lateinit var settingsBinding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        settingsBinding = ActivitySettingsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(settingsBinding.root)

        settingsBinding.topAppBar.setNavigationOnClickListener {
            finish()
        }

        storedLang = SharedStorage(application)

        if (storedLang.language == "en") {
            settingsBinding.btnEnglish.isChecked = true
            settingsBinding.materialText.text = resources.getString(R.string.language)
        } else {
            settingsBinding.btnBengali.isChecked = true
            settingsBinding.materialText.text = resources.getString(R.string.language)
        }

        settingsBinding.btgLanguage.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btnEnglish -> {
                        LanguageChange.setLocale(this, "en")
                        Intent(this, SettingsActivity::class.java).also {
                            finish()
                            overridePendingTransition(0, 0)
                            startActivity(it)
                            overridePendingTransition(0, 0)
                        }
                    }
                    else -> {
                        LanguageChange.setLocale(this, "bn")
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