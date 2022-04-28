package com.example.loginpage.presentation

import android.os.AsyncTask.execute
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.loginpage.domain.use_case.ValidateEmail
import com.example.loginpage.domain.use_case.ValidatePassword
import com.example.loginpage.domain.use_case.ValidateRepeatedPassword
import com.example.loginpage.domain.use_case.ValidateTerms
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val validateRepeatedPassword: ValidateRepeatedPassword = ValidateRepeatedPassword(),
    private val validateTerms: ValidateTerms = ValidateTerms()
): ViewModel() {
    var state by mutableStateOf(LoginFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val valudationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: LoginFormEvent) {
        when(event){
            is LoginFormEvent.EmailChanged ->{
                state = state.copy(email = event.email)
            }
            is LoginFormEvent.PasswordChanged ->{
                state = state.copy(password = event.password)
            }
            is LoginFormEvent.RepeatedPasswordChanged ->{
                state = state.copy(repeatedPassword = event.repeatedPassword)
            }
            is LoginFormEvent.AcceptTerms ->{
                state = state.copy(acceptedTerms = event.isAccepted)
            }
            is LoginFormEvent.Submit->{
                subMitData()
            }
        }
    }

    private fun subMitData() {
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)
        val repeatedPasswordResult = validateRepeatedPassword.execute(state.password, state.repeatedPassword)
        val termsResult = validateTerms.execute(state.acceptedTerms)

        val hasError = listOf(
            emailResult,
            passwordResult,
            repeatedPasswordResult,
            termsResult
        ).any { !it.success }

        if(hasError){
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                repeatedPasswordError = repeatedPasswordResult.errorMessage,
                termError = termsResult.errorMessage
            )
            return
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    sealed class ValidationEvent{
        object Success: ValidationEvent()
    }

}