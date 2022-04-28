package com.example.loginpage.domain.use_case

class ValidateTerms {
    fun execute(acceptedTerms: Boolean): ValidateResult {
        if (!acceptedTerms) {
            return ValidateResult(
                success = false,
                errorMessage = "Please accept the terms"
            )
        }
        return ValidateResult(
            success = true
        )
    }
}