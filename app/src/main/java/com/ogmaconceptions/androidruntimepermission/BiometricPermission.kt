package com.ogmaconceptions.androidruntimepermission

import android.app.KeyguardManager
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.ogmaconceptions.androidruntimepermission.databinding.ActivityBiometricPermissionBinding
import java.util.concurrent.Executor


class BiometricPermission : AppCompatActivity() {
    private val TAG = BiometricPermission::class.java.simpleName
    private lateinit var biometricBinding: ActivityBiometricPermissionBinding
    // create a CancellationSignal variable and assign a value null to it
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private val promptInfo = BiometricPrompt.PromptInfo.Builder()
        .setTitle("Biometric login for my app")
        .setSubtitle("Log in using your biometric credential")
        .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
        .build()
    private val PREF_NAME = "biometric-check"
    private lateinit var sharedPref: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        biometricBinding = ActivityBiometricPermissionBinding.inflate(layoutInflater)
        setContentView(biometricBinding.root)

        sharedPref = this.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE)

        checkBiometricSupport()

        executor = ContextCompat.getMainExecutor(this)

        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int,
                                                   errString: CharSequence) {

                    biometricBinding.materialSwitch.isChecked = false
                    Snackbar.make(
                        biometricBinding.constraintLayout,
                        "Authentication Error: $errString",
                        Snackbar.LENGTH_SHORT
                    ).show()

                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    val editor = sharedPref.edit()
                    editor.putBoolean(PREF_NAME, true)
                    editor.apply()
                    Snackbar.make(
                        biometricBinding.constraintLayout,
                        "Authentication succeeded!",
                        Snackbar.LENGTH_SHORT
                    ).show()
                }

            })

        //Log.e(TAG,"CURRENTSHAREDPREFERENCEVALUE ${sharedPref.getBoolean(PREF_NAME,false)}")
        if (sharedPref.getBoolean(PREF_NAME, false)) {
            biometricBinding.materialSwitch.isChecked = true
        }


        biometricBinding.topAppBar.setNavigationOnClickListener {
            finish()
        }

        biometricBinding.materialSwitch.setOnCheckedChangeListener { compoundButton, b ->
            Log.e(TAG, "CHECKCHANGE $b")
            if (b) {
                biometricBinding.materialSwitch.isChecked = true
                biometricPrompt.authenticate(promptInfo)
            } else {
                val editor = sharedPref.edit()
                editor.clear()
                editor.apply()
            }
        }

        biometricBinding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.forward -> {
                    Intent(this, LoginActivity::class.java).also { intent ->
                        startActivity(intent)

                    }
                    true
                }
                else -> {
                    false
                }
            }
        }

    }


    // it checks whether the app the app has fingerprint permission
    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkBiometricSupport(): Boolean {
        val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if (!keyguardManager.isDeviceSecure) {
            Snackbar.make(
                biometricBinding.constraintLayout,
                "Fingerprint authentication has not been enabled in settings",
                Snackbar.LENGTH_INDEFINITE
            ).setAction("Dismiss") {}
                .show()
            return false
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED) {
            Snackbar.make(
                biometricBinding.constraintLayout,
                "Fingerprint Authentication Permission is not enabled",
                Snackbar.LENGTH_INDEFINITE
            ).setAction("Dismiss") {}
                .show()
            return false

        }
        return if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            true
        } else {
            Snackbar.make(
                biometricBinding.constraintLayout,
                "Fingerprint not supported in this device Open using System Lock",
                Snackbar.LENGTH_INDEFINITE
            ).setAction("Dismiss") {}
                .show()
            return true
        }
    }

}