package com.ogmaconceptions.androidruntimepermission.utils

import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.google.android.material.textfield.TextInputLayout

object TextInputLayoutUtils {
    fun resetTextInputErrorsOnTextChanged(view: ViewGroup) {
        for (i in 0 until view.childCount) {
            if (view.getChildAt(i) is ViewGroup) {
                if (view.getChildAt(i) is TextInputLayout) {
                    //Log.e("POSITION","$i")
                    val item = view.getChildAt(i) as TextInputLayout
                    item.editText?.let {
                        it.doOnTextChanged { text, _, _, _ ->
                            if (!text.isNullOrEmpty()) {
                                item.error = null
                                for (j in 0 until view.childCount) {
                                    if (view.getChildAt(j) is TextInputLayout) {
                                        //Log.e("POSITION > J","$j")
                                        val otherItems = view.getChildAt(j) as TextInputLayout
                                        otherItems.error = null
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
}
