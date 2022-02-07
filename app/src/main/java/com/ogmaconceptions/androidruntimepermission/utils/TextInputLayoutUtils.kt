package com.ogmaconceptions.androidruntimepermission.utils

import android.util.Log
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputLayout

class TextInputLayoutUtils {
    var errorFound = false
    var parentView: ViewGroup? = null


    fun resetTextInputErrorsOnTextChanged(view: ViewGroup) {
        for (i in 0 until view.childCount) {
            if (view.getChildAt(i) is ViewGroup) {
                if (view.getChildAt(i) is TextInputLayout) {
                    val item = view.getChildAt(i) as TextInputLayout
                    item.error = null
                    item.editText?.let {
                        it.doOnTextChanged { text, _, _, _ ->
                            if (!text.isNullOrEmpty()) {
                                if (!errorFound) {
                                    parentView?.let { it1 -> childLoop(it1) }
                                }
                            }
                        }
                    }
                } else {
                    resetTextInputErrorsOnTextChanged(view.getChildAt(i) as ViewGroup)
                }
            }
        }
    }

    private fun childLoop(view: ViewGroup) {
        for (j in 0 until view.childCount) {
            if (view.getChildAt(j) is ViewGroup) {
                if (view.getChildAt(j) is TextInputLayout) {
                    Log.e("POSITION > J", "$j")
                    val otherItems = view.getChildAt(j) as TextInputLayout
                    otherItems.error = null
                    errorFound = true
                } else {
                    resetTextInputErrorsOnTextChanged(view.getChildAt(j) as ViewGroup)
                }
            }
        }
    }


}
