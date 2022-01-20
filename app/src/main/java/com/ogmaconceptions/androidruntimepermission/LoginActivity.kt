package com.ogmaconceptions.androidruntimepermission

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.ogmaconceptions.androidruntimepermission.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {
    private val TAG = LoginActivity::class.java.simpleName
    private lateinit var loginBinding: ActivityLoginBinding
    private var emailValidate = false
    private var passwordValidate = false
    private val PREF_NAME = "MultiLingual"
    private val nameArray = arrayOf("Prasenjit", "Avishek", "Sajjad", "Jishnu")
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        loginBinding.topAppBar.setNavigationOnClickListener {
            Intent(this, BiometricPermission::class.java).also {
                startActivity(it)
            }
        }

        sharedPref = this.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        if (sharedPref.getString(PREF_NAME, "en") == "en") {
            loginBinding.btnEnglish.isChecked = true
            Locale.changeLanguage("en", this)
        } else {
            loginBinding.btnBengali.isChecked = true
            Locale.changeLanguage("bn", this)
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

        val email: String? = intent.getStringExtra("emailValue")

        email?.let {
            loginBinding.eTxtEmail.setText(it)
        }

        val password: String? = intent.getStringExtra("passwordValue")

        password?.let {
            loginBinding.eTxtPassword.setText(it)
        }

        val emailValidation: Boolean? = intent.extras?.getBoolean("emailValidation")
        emailValidation?.let {
            emailValidate = it
        }

        val passwordValidation: Boolean? = intent.extras?.getBoolean("passwordValidation")
        passwordValidation?.let {
            passwordValidate = it
        }

        val personName: String? = intent.getStringExtra("welcomePerson")
        personName?.let {
            loginBinding.tvWelcomeName.text = it
        }

        loginBinding.eTxtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                emailValidate =
                    checkEmail(loginBinding.eTxtEmail.text.toString(), loginBinding.layoutEmail)
            }

        })

        loginBinding.eTxtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                passwordValidate = checkPassword(
                    loginBinding.eTxtPassword.text.toString(),
                    loginBinding.layoutPassword
                )
            }

        })

        loginBinding.btnSignIn.setOnClickListener {
            if (emailValidate && passwordValidate) {
                Snackbar.make(
                    loginBinding.constraintLayout,
                    this.resources.getString(R.string.loginSucess),
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("Dismiss") {}.show()
            } else {
                Snackbar.make(
                    loginBinding.constraintLayout,
                    "validation issue",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction("Dismiss") {}.show()
            }
        }

    }

    private fun reloadActivity() {
        Intent(this, LoginActivity::class.java).also {
            it.putExtra("emailValue", loginBinding.eTxtEmail.text.toString())
            it.putExtra("emailValidation", emailValidate)
            it.putExtra("passwordValue", loginBinding.eTxtPassword.text.toString())
            it.putExtra("passwordValidation", passwordValidate)
            it.putExtra("welcomePerson", loginBinding.tvWelcomeName.text)
            finish()
            overridePendingTransition(0, 0)
            startActivity(it)
            overridePendingTransition(0, 0)
        }
    }

    private fun checkPassword(password: String, layoutPassword: TextInputLayout): Boolean {
        return when {
            password.isEmpty() -> {
                layoutPassword.error = this.resources.getString(R.string.passwordBlankValidation)
                false
            }
            password.length < 8 -> {
                layoutPassword.error = this.resources.getString(R.string.passwordFormatValidation)
                false
            }
            else -> {
                layoutPassword.error = null
                true
            }
        }
    }

    private fun checkEmail(email: String, layoutEmail: TextInputLayout): Boolean {
        return when {
            email.isEmpty() -> {
                layoutEmail.error = this.resources.getString(R.string.emailBlankValidation)
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                layoutEmail.error = this.resources.getString(R.string.emailFormatValidation)
                false
            }
            else -> {
                layoutEmail.error = null
                true
            }
        }
    }

}