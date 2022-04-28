package com.example.loginpage.domain.use_case

import android.util.Patterns
import androidx.compose.runtime.snapshots.SnapshotApplyResult


class ValidateEmail {
    fun execute(email: String): ValidateResult {
        if (email.isBlank()) {
            return ValidateResult(
                success = false,
                errorMessage = "fill up the Email"
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidateResult(
                success = false,
                errorMessage = "That's not a valid email"
            )
        }
        return ValidateResult(
            success = true
        )
    }
}