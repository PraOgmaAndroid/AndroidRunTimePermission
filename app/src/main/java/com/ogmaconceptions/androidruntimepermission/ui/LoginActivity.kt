package com.ogmaconceptions.androidruntimepermission.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import com.google.android.material.snackbar.Snackbar
import com.ogmaconceptions.androidruntimepermission.R
import com.ogmaconceptions.androidruntimepermission.databinding.ActivityLoginBinding
import com.ogmaconceptions.androidruntimepermission.utils.BaseActivity
import com.ogmaconceptions.androidruntimepermission.utils.LanguageChange
import com.ogmaconceptions.androidruntimepermission.utils.SharedStorage


class LoginActivity : BaseActivity() {
    private val TAG = LoginActivity::class.java.simpleName
    private lateinit var loginBinding: ActivityLoginBinding
    private var emailValidate = false
    private var passwordValidate = false
    private val nameArray = arrayOf("Prasenjit", "Avishek", "Sajjad", "Jishnu")
    private var passwordErrorMessageId: Int? = null
    private var emailErrorMessageId: Int? = null
    private var instanceState: Bundle? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        if (SharedStorage.getStoredLanguage(this) == "en") {
            loginBinding.btnEnglish.isChecked = true
        } else {
            loginBinding.btnBengali.isChecked = true
        }

        loginBinding.topAppBar.setNavigationOnClickListener {
            finish()
        }

        loginBinding.tvWelcomeName.text = nameArray.random()

        loginBinding.btgLanguage.addOnButtonCheckedListener { group, checkedId, isChecked ->
            if (isChecked) {
                when (checkedId) {
                    R.id.btnEnglish -> {
                        LanguageChange.changeLanguage("en", this)
                        SharedStorage.storeLanguage(this, "en")
                        reloadActivity()
                    }
                    else -> {
                        LanguageChange.changeLanguage("bn", this)
                        SharedStorage.storeLanguage(this, "bn")
                        reloadActivity()
                    }
                }

            }
        }

        getIntentValue()

        loginBinding.btnSignIn.setOnClickListener {

            emailValidate =
                checkEmail(loginBinding.eTxtEmail.text.toString())
            passwordValidate =
                checkPassword(loginBinding.eTxtPassword.text.toString())

            loginBinding.layoutEmail.error = emailErrorMessageId?.let { it1 ->
                resources.getString(
                    it1
                )
            }
            loginBinding.layoutPassword.error = passwordErrorMessageId?.let { it1 ->
                resources.getString(
                    it1
                )
            }

            if (emailValidate && passwordValidate) {
                Snackbar.make(
                    loginBinding.constraintLayout,
                    this.resources.getString(R.string.loginSucess),
                    Snackbar.LENGTH_SHORT
                ).show()
                Intent(this, SettingsActivity::class.java).also { startActivity(it) }
            }
        }

    }

    private fun getIntentValue() {
        with(intent) {
            val email: String? = getStringExtra("emailValue")

            email?.let {
                loginBinding.eTxtEmail.setText(it)
            }

            val password: String? = getStringExtra("passwordValue")

            password?.let {
                loginBinding.eTxtPassword.setText(it)
            }

            emailErrorMessageId = getIntExtra("emailErrorMessage", 0)
            try {
                loginBinding.layoutEmail.error = resources.getString(emailErrorMessageId!!)
            } catch (e: Exception) {
                Log.e(TAG, "${e.localizedMessage} $emailErrorMessageId")
            }

            passwordErrorMessageId = getIntExtra("passwordErrorMessage", 0)
            try {
                loginBinding.layoutPassword.error = resources.getString(passwordErrorMessageId!!)
            } catch (e: Exception) {
                Log.e(TAG, "${e.localizedMessage} $passwordErrorMessageId")
            }

            val personName: String? = intent.getStringExtra("welcomePerson")
            personName?.let {
                loginBinding.tvWelcomeName.text = it
            }
        }

    }


    private fun reloadActivity() {
        Intent(this, LoginActivity::class.java).apply {
            putExtra("emailValue", loginBinding.eTxtEmail.text.toString())
            putExtra("emailErrorMessage", emailErrorMessageId)
            putExtra("passwordValue", loginBinding.eTxtPassword.text.toString())
            putExtra("passwordErrorMessage", passwordErrorMessageId)
            putExtra("welcomePerson", loginBinding.tvWelcomeName.text)
        }.also {
            finish()
            overridePendingTransition(0, 0)
            startActivity(it)
            overridePendingTransition(0, 0)
        }
    }

    private fun checkPassword(password: String): Boolean {
        return when {
            password.isEmpty() -> {
                passwordErrorMessageId = R.string.passwordBlankValidation
                false
            }
            password.length < 8 -> {
                passwordErrorMessageId = R.string.passwordFormatValidation
                false
            }
            else -> {
                passwordErrorMessageId = null
                true
            }
        }
    }

    private fun checkEmail(email: String): Boolean {
        return when {
            email.isEmpty() -> {
                emailErrorMessageId = R.string.emailBlankValidation
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                emailErrorMessageId = R.string.emailFormatValidation
                false
            }
            else -> {
                emailErrorMessageId = null
                true
            }
        }
    }


}