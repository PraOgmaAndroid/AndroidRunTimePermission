package com.ogmaconceptions.androidruntimepermission.utils

import android.util.Log
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputLayout

object TextInputLayoutUtils {
    fun resetTextInputErrorsOnTextChanged(view: ViewGroup) {
        for (i in 0 until view.childCount) {
            if (view.getChildAt(i) is ViewGroup) {
                if (view.getChildAt(i) is TextInputLayout) {
                    Log.e("POSITION -> I", "$i")
                    val item = view.getChildAt(i) as TextInputLayout
                    item.error = null
                    item.editText?.let {
                        it.doOnTextChanged { text, _, _, _ ->
                            if (!text.isNullOrEmpty()) {
                                for (j in 0 until view.childCount) {
                                    if (view.getChildAt(j) is ViewGroup) {
                                        if (view.getChildAt(j) is TextInputLayout) {
                                            Log.e("POSITION > J", "$j")
                                            val otherItems = view.getChildAt(j) as TextInputLayout
                                            otherItems.error = null
                                        } else {
                                            val view2 = view.getChildAt(j) as ViewGroup
                                            for (k in 0 until view2.childCount) {
                                                Log.e("PRINT -> K", "$k")
                                                if (view2.getChildAt(k) is ViewGroup) {
                                                    if (view2.getChildAt(k) is TextInputLayout) {
                                                        val insideOtherViews =
                                                            view2.getChildAt(k) as TextInputLayout
                                                        insideOtherViews.error = null
                                                        insideOtherViews.editText?.let { editTxt ->
                                                            editTxt.doOnTextChanged { text, _, _, _ ->
                                                                if (!text.isNullOrEmpty()) {
                                                                    resetTextInputErrorsOnTextChanged(
                                                                        view.getChildAt(i) as ViewGroup
                                                                    )
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
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

    private fun checkFunction(view: ViewGroup) {

    }
}
