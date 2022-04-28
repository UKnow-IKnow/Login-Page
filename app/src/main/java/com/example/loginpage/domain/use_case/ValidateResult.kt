package com.example.loginpage.domain.use_case

data class ValidateResult(
    val success: Boolean,
    val errorMessage: String? = null
)
