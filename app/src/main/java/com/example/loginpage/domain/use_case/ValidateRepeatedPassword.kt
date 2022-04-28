package com.example.loginpage.domain.use_case

class ValidateRepeatedPassword {
    fun execute(password: String, repeatedpassword:String): ValidateResult {

        if (password != repeatedpassword) {
            return ValidateResult(
                success = false,
                errorMessage = "The password don't match"
            )
        }
        return ValidateResult(
            success = true
        )
    }
}