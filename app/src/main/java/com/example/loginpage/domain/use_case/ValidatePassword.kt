package com.example.loginpage.domain.use_case

import android.util.Patterns

class ValidatePassword {
    fun execute(password: String): ValidateResult {
        if (password.length < 8) {
            return ValidateResult(
                success = false,
                errorMessage = "Password need to 8 character"
            )
        }
        val containCharacterAndDigits =
            password.any { it.isDigit() } && password.all { it.isLetter() }
        if (!containCharacterAndDigits) {
            return ValidateResult(
                success = false,
                errorMessage = "The password need to contain atLeast one digit and character"
            )
        }
        return ValidateResult(
            success = true
        )
    }
}