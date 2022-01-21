package com.ogmaconceptions.androidruntimepermission

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.ogmaconceptions.androidruntimepermission.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    private val TAG = LoginActivity::class.java.simpleName
    private lateinit var loginBinding: ActivityLoginBinding
    private var emailValidate = false
    private var passwordValidate = false
    private val PREF_NAME = "MultiLingual"
    private val nameArray = arrayOf("Prasenjit", "Avishek", "Sajjad", "Jishnu")
    private lateinit var sharedPref: SharedPreferences
    private var passwordErrorMessageId: Int? = null
    private var emailErrorMessageId: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        sharedPref = this.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        if (sharedPref.getString(PREF_NAME, "en") == "en") {
            Locale.changeLanguage("en", this)
            super.onCreate(savedInstanceState)
            loginBinding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(loginBinding.root)
            loginBinding.btnEnglish.isChecked = true

        } else {
            Locale.changeLanguage("bn", this)
            super.onCreate(savedInstanceState)
            loginBinding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(loginBinding.root)
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
                        Locale.changeLanguage("en", this)
                        val editor = sharedPref.edit()
                        editor.putString(PREF_NAME, "en")
                        editor.apply()
                        reloadActivity()
                    }
                    else -> {
                        Locale.changeLanguage("bn", this)
                        val editor = sharedPref.edit()
                        editor.putString(PREF_NAME, "bn")
                        editor.apply()
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
        Intent(this, LoginActivity::class.java).also {
            it.putExtra("emailValue", loginBinding.eTxtEmail.text.toString())
            it.putExtra("emailErrorMessage", emailErrorMessageId)
            it.putExtra("passwordValue", loginBinding.eTxtPassword.text.toString())
            it.putExtra("passwordErrorMessage", passwordErrorMessageId)
            it.putExtra("welcomePerson", loginBinding.tvWelcomeName.text)

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