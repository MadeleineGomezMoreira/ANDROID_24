package com.example.busapiclienttokenscompose.ui.screens.login

import com.example.busapiclienttokenscompose.domain.model.LoginDTO
import com.example.busapiclienttokenscompose.domain.model.RegisterDTO

class LoginContract {

    data class LoginState(
        val isLoading: Boolean = false,
        val error: String? = null,
        val userId: Int? = null,
        val userToLogin: LoginDTO? = LoginDTO(),
        val userToRegister: RegisterDTO? = RegisterDTO(),
        val isLoginOk: Boolean = false,
        val isRegisterOk: Boolean = false,
    )

    sealed class LoginEvent {
        class IntroducedLoginData(val loginData: LoginDTO?) : LoginEvent()
        class IntroducedRegisterData(val registerData: RegisterDTO) : LoginEvent()
        data object LoginUser : LoginEvent()
        data object RegisterUser : LoginEvent()
        data object ErrorDisplayed : LoginEvent()
        data object LoginSuccessful : LoginEvent()
        data object RegisterWasSuccessful : LoginEvent()
        data object LoginWasSuccessful : LoginEvent()
    }
}