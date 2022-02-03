package com.ogmaconceptions.androidruntimepermission.utils

import android.content.Context
import android.util.Patterns
import com.google.android.material.textfield.TextInputLayout

object Validation {
    fun checkValidation(
        context: Context,
        validationField: FieldConstants,
        textFromField: String,
        textInputLayout: TextInputLayout,
        blankFieldErrorMsg: Int,
        validationErrorMsg: Int? = null,
        confirmPasswordText: String = "",
        confirmPasswordTextErrorMsg: Int? = null
    ): ValidationReturnTypes {
        when (validationField) {
            FieldConstants.FIRSTNAME -> {
                return when {
                    textFromField.isEmpty() -> {
                        textInputLayout.error = context.resources.getString(blankFieldErrorMsg)
                        ValidationReturnTypes(false)
                    }
                    else -> {
                        textInputLayout.error = null
                        ValidationReturnTypes(true)
                    }
                }
            }
            FieldConstants.LASTNAME -> {
                return when {
                    textFromField.isEmpty() -> {
                        textInputLayout.error = context.resources.getString(blankFieldErrorMsg)
                        ValidationReturnTypes(false)
                    }
                    else -> {
                        textInputLayout.error = null
                        ValidationReturnTypes(true)
                    }
                }
            }
            FieldConstants.PHONE -> {
                return when {
                    textFromField.isEmpty() -> {
                        textInputLayout.error = context.resources.getString(blankFieldErrorMsg)
                        ValidationReturnTypes(false)
                    }
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
                    textFromField.isEmpty() -> {
                        textInputLayout.error = context.resources.getString(blankFieldErrorMsg)
                        ValidationReturnTypes(false)
                    }
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
            FieldConstants.ADDRESS -> {
                return when {
                    textFromField.isEmpty() -> {
                        textInputLayout.error = context.resources.getString(blankFieldErrorMsg)
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
                    textFromField.isEmpty() -> {
                        textInputLayout.error = context.resources.getString(blankFieldErrorMsg)
                        ValidationReturnTypes(false)
                    }
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
                    textFromField.isEmpty() -> {
                        textInputLayout.error = context.resources.getString(blankFieldErrorMsg)
                        ValidationReturnTypes(false)
                    }
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
        }
    }
}