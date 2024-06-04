package com.example.busapiclienttokenscompose.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bus_api_client_xml.utils.NetworkResult
import com.example.busapiclienttokenscompose.domain.model.LoginDTO
import com.example.busapiclienttokenscompose.domain.model.RegisterDTO
import com.example.busapiclienttokenscompose.domain.usecases.auth.LoginUseCase
import com.example.busapiclienttokenscompose.domain.usecases.auth.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val login: LoginUseCase,
    private val register: RegisterUseCase,
) : ViewModel() {

    private val _state: MutableStateFlow<LoginContract.LoginState> by lazy {
        MutableStateFlow(LoginContract.LoginState())
    }

    val state: StateFlow<LoginContract.LoginState> = _state.asStateFlow()

    fun handleEvent(event: LoginContract.LoginEvent) {
        when (event) {
            LoginContract.LoginEvent.ErrorDisplayed -> _state.value =
                _state.value.copy(error = null, isLoginOk = false)

            is LoginContract.LoginEvent.LoginUser -> userLogin()
            is LoginContract.LoginEvent.RegisterUser -> userRegister()
            is LoginContract.LoginEvent.IntroducedRegisterData -> introducedRegisterData(event.registerData)
            is LoginContract.LoginEvent.IntroducedLoginData -> introducedLoginData(event.loginData)
            is LoginContract.LoginEvent.LoginWasSuccessful -> _state.value =
                _state.value.copy(isLoginOk = false)

            is LoginContract.LoginEvent.LoginSuccessful -> _state.value =
                _state.value.copy(isLoginOk = true)

            LoginContract.LoginEvent.RegisterWasSuccessful -> _state.value =
                _state.value.copy(isRegisterOk = false)
        }
    }

    //TODO: check if the fields are empty in the useCase

    private fun userLogin() {
        _state.value.userToLogin?.let { userToLogin ->
            viewModelScope.launch {
                login.invoke(userToLogin).collect { result ->
                        when (result) {
                            is NetworkResult.Error -> {
                                _state.value = _state.value.copy(
                                    error = result.message, isLoading = false, isLoginOk = false
                                )
                            }

                            is NetworkResult.Loading -> {
                                _state.value = _state.value.copy(
                                    isLoading = true, isLoginOk = false
                                )
                            }

                            is NetworkResult.Success -> {
                                _state.value = _state.value.copy(
                                    isLoading = false, userId = result.data, isLoginOk = true
                                )
                            }
                        }
                    }
            }
        }
    }

    private fun userRegister() {
        _state.value.userToRegister?.let { userToRegister ->
            viewModelScope.launch {
                register.invoke(userToRegister).collect { result ->
                        when (result) {
                            is NetworkResult.Error -> {
                                _state.value = _state.value.copy(
                                    error = result.message, isLoading = false, isRegisterOk = false
                                )
                            }

                            is NetworkResult.Loading -> {
                                _state.value = _state.value.copy(
                                    isLoading = true, isRegisterOk = false
                                )
                            }

                            is NetworkResult.Success -> {
                                _state.value = _state.value.copy(
                                    isLoading = false, isRegisterOk = true
                                )
                            }
                        }
                    }
            }
        }
    }


    private fun introducedPasswordLogin(passwordIntroduced: String) {
        _state.update {
            it.copy(
                userToLogin = it.userToLogin?.copy(password = passwordIntroduced) ?: LoginDTO(
                    password = passwordIntroduced
                )
            )
        }
    }

    private fun introducedLoginData(loginData: LoginDTO?) {
        _state.update {
            it.copy(
                userToLogin =loginData
                )

        }
    }

    private fun introducedRegisterData(registerData: RegisterDTO) {
        _state.update {
            it.copy(
                userToRegister = it.userToRegister?.copy(
                    username = registerData.username,
                    password = registerData.password,
                    email = registerData.email,
                    phone = registerData.phone,
                    firstName = registerData.firstName,
                    lastName = registerData.lastName
                ) ?: registerData
            )
        }
    }

}