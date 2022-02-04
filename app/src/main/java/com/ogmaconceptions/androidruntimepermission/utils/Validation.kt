package com.ogmaconceptions.androidruntimepermission.utils

import android.content.Context
import android.util.Patterns
import com.google.android.material.textfield.TextInputLayout

object Validation {
    fun checkValidation(
        context: Context,
        textFromField: String,
        textInputLayout: TextInputLayout,
        blankFieldErrorMsg: Int,
        validationField: FieldConstants? = null,
        validationErrorMsg: Int? = null,
        confirmPasswordText: String = "",
        confirmPasswordTextErrorMsg: Int? = null
    ): ValidationReturnTypes {
        if (textFromField.isEmpty()) {
            textInputLayout.error = context.resources.getString(blankFieldErrorMsg)
            return ValidationReturnTypes(false)
        } else {
            when (validationField) {
                FieldConstants.PHONE -> {
                    return when {
                        textFromField.length < 10 -> {
                            textInputLayout.error =
                                validationErrorMsg?.let { context.resources.getString(it) }
                            ValidationReturnTypes(false)
                        }
                        else -> {
                            textInputLayout.error = null
                            ValidationReturnTypes(true)
                        }
                    }
                }
                FieldConstants.EMAIL -> {
                    return when {
                        !Patterns.EMAIL_ADDRESS.matcher(textFromField).matches() -> {
                            textInputLayout.error = validationErrorMsg?.let {
                                context.resources.getString(
                                    it
                                )
                            }
                            ValidationReturnTypes(false)
                        }
                        else -> {
                            textInputLayout.error = null
                            ValidationReturnTypes(true)
                        }
                    }
                }
                FieldConstants.PASSWORD -> {
                    return when {
                        textFromField.length < 8 -> {
                            textInputLayout.error = validationErrorMsg?.let {
                                context.resources.getString(
                                    it
                                )
                            }
                            ValidationReturnTypes(false)
                        }
                        else -> {
                            textInputLayout.error = null
                            ValidationReturnTypes(true)
                        }
                    }
                }
                FieldConstants.CONFIRM_PASSWORD -> {
                    return when {
                        textFromField.length < 8 -> {
                            textInputLayout.error = validationErrorMsg?.let {
                                context.resources.getString(
                                    it
                                )
                            }
                            ValidationReturnTypes(false)
                        }
                        textFromField != confirmPasswordText -> {
                            textInputLayout.error = confirmPasswordTextErrorMsg?.let {
                                context.resources.getString(
                                    it
                                )
                            }
                            ValidationReturnTypes(false)
                        }
                        else -> {
                            textInputLayout.error = null
                            ValidationReturnTypes(true)
                        }
                    }
                }
                else -> {
                    return ValidationReturnTypes(true)
                }
            }
        }
    }
}