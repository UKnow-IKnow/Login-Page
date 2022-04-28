package com.example.loginpage.presentation

sealed class LoginFormEvent {
    data class EmailChanged(val email: String) : LoginFormEvent()
    data class PasswordChanged(val password: String) : LoginFormEvent()
    data class RepeatedPasswordChanged(val repeatedPassword: String) : LoginFormEvent()
    data class AcceptTerms(val isAccepted: Boolean) : LoginFormEvent()

    object Submit: LoginFormEvent()
}
