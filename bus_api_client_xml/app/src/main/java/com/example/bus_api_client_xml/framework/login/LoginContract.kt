package com.example.bus_api_client_xml.framework.login

import com.example.bus_api_client_xml.data.model.RegisterDTO

class LoginContract {

    data class LoginState(
        val correctLogin: Boolean = false,
        val loading: Boolean = false,
        val error: String? = null,
    )

    sealed class LoginEvent {
        class LoginUser(val username: String, val password: String) : LoginEvent()
        class RegisterUser(val registerData: RegisterDTO) : LoginEvent()
        data object ErrorDisplayed : LoginEvent()
    }
}