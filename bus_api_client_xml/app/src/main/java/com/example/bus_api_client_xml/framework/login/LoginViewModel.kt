package com.example.bus_api_client_xml.framework.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bus_api_client_xml.data.model.LoginDTO
import com.example.bus_api_client_xml.data.model.RegisterDTO
import com.example.bus_api_client_xml.domain.usecases.auth.LoginUseCase
import com.example.bus_api_client_xml.domain.usecases.auth.RegisterUseCase
import com.example.bus_api_client_xml.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val login: LoginUseCase,
    private val register: RegisterUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<LoginContract.LoginState> by lazy {
        MutableStateFlow(LoginContract.LoginState())
    }

    val state: StateFlow<LoginContract.LoginState> = _state

    fun handleEvent(event: LoginContract.LoginEvent) {
        when (event) {
            LoginContract.LoginEvent.ErrorDisplayed -> _state.value =
                _state.value.copy(error = null, correctLogin = false)

            is LoginContract.LoginEvent.LoginUser -> userLogin(event.username, event.password)
            is LoginContract.LoginEvent.RegisterUser -> userRegister(event.registerData)
        }
    }

    private fun userLogin(username: String, password: String) {
        viewModelScope.launch {
            login.invoke(LoginDTO(username, password))
                .collect { result ->
                    when (result) {
                        is NetworkResult.Error -> {
                            _state.value = _state.value.copy(
                                error = result.message
                            )
                        }
                        is NetworkResult.Loading -> {
                            _state.value = _state.value.copy(
                                loading = true
                            )
                        }
                        is NetworkResult.Success -> {
                            _state.value = _state.value.copy(
                                correctLogin = true
                            )
                        }
                    }
                }
        }
    }

    private fun userRegister(registerData: RegisterDTO) {
        viewModelScope.launch {
            register.invoke(registerData)
                .collect { result ->
                    when (result) {
                        is NetworkResult.Error -> {
                            _state.value = _state.value.copy(
                                error = result.message
                            )
                        }

                        is NetworkResult.Loading -> {
                            _state.value = _state.value.copy(
                                loading = true
                            )
                        }

                        else -> {
                            _state.value = _state.value.copy(
                                loading = false,
                            )
                        }
                    }
                }
        }
    }

}