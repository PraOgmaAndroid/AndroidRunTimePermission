package com.ogmaconceptions.androidruntimepermission.ui
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.ogmaconceptions.androidruntimepermission.R
import com.ogmaconceptions.androidruntimepermission.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    private lateinit var viewBinding: ActivityMainBinding
    private lateinit var view: View
    private val PERMISSION_REQUEST_CODE = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        view = viewBinding.root
        setContentView(viewBinding.root)

        viewBinding.materialButton.setOnClickListener {
            if(!checkPermission()){
                requestPermission()
            }

        }

        viewBinding.topAppBar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.forward -> {
                    Intent(this, BiometricPermission::class.java).also { intent ->
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

    private fun checkPermission():Boolean{
        val resultCamera = ContextCompat.checkSelfPermission(
            this,Manifest.permission.CAMERA
        )
        val resultLocation = ContextCompat.checkSelfPermission(
            this,Manifest.permission.ACCESS_FINE_LOCATION
        )

        return resultCamera == PackageManager.PERMISSION_GRANTED &&
                resultLocation == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission(){
        ActivityCompat.requestPermissions(
            this@MainActivity, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CAMERA
            ), PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        Log.e(TAG,"CODE -> $requestCode")

        when(requestCode){
            PERMISSION_REQUEST_CODE -> if(grantResults.isNotEmpty()){
                val locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED

                if(locationAccepted && cameraAccepted){
                    showSnackbar(
                        view,
                        getString(R.string.permission_granted),
                        Snackbar.LENGTH_INDEFINITE,
                        action = null
                    )
                }else {
                    when {
                        shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                            showSnackbar(
                                view,
                                getString(R.string.permission_required_location),
                                Snackbar.LENGTH_INDEFINITE,
                                getString(R.string.ok)
                            ) {
                                requestPermission()
                            }
                        }
                        shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> {
                            showSnackbar(
                                view,
                                getString(R.string.permission_required),
                                Snackbar.LENGTH_INDEFINITE,
                                getString(R.string.ok)
                            ) {
                                requestPermission()
                            }
                        }
                        else -> {
                            openSystemSettings()
                        }
                    }
                }

            }
        }
    }

    private fun openSystemSettings() {
        MaterialAlertDialogBuilder(this)
            .setTitle(resources.getString(R.string.title))
            .setMessage(resources.getString(R.string.message))
            .setNeutralButton(resources.getString(R.string.cancel)) { dialog, which ->
                dialog.dismiss()
            }

            .setPositiveButton(resources.getString(R.string.accept)) { dialog, which ->
                startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", packageName, null)
                })
                dialog.dismiss()
            }.show()
    }

    fun showSnackbar(
        view: View,
        msg: String,
        length: Int,
        actionMessage: String = "",
        action: ((View) -> Unit)?
    ) {
        val snackbar = Snackbar.make(view, msg, length)
        if (actionMessage != null) {
            snackbar.setAction(actionMessage) {
                if (action != null) {
                    action(view)
                }
            }.show()
        } else {
            snackbar.show()
        }
    }

}




