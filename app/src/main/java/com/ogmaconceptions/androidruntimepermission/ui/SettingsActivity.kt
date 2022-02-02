package com.ogmaconceptions.androidruntimepermission.ui


import android.content.Intent
import android.os.Bundle
import com.ogmaconceptions.androidruntimepermission.R
import com.ogmaconceptions.androidruntimepermission.databinding.ActivitySettingsBinding
import com.ogmaconceptions.androidruntimepermission.utils.BaseActivity
import com.ogmaconceptions.androidruntimepermission.utils.LanguageChange
import com.ogmaconceptions.androidruntimepermission.utils.LoginData
import com.ogmaconceptions.androidruntimepermission.utils.SharedStorage


class SettingsActivity : BaseActivity() {

    private lateinit var settingsBinding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        settingsBinding = ActivitySettingsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(settingsBinding.root)

        settingsBinding.tvEmail.text = LoginData.email

        settingsBinding.tvPassword.text = LoginData.password

        settingsBinding.topAppBar.setNavigationOnClickListener {
            finish()
        }

        if (SharedStorage.getStoredLanguage(this) == "en") {
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
                        LanguageChange.changeLanguage("en", this)
                        SharedStorage.storeLanguage(this, "en")
                        Intent(this, SettingsActivity::class.java).also {
                            finish()
                            overridePendingTransition(0, 0)
                            startActivity(it)
                            overridePendingTransition(0, 0)
                        }
                    }
                    else -> {
                        LanguageChange.changeLanguage("bn", this)
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