package com.example.busapiclienttokenscompose.ui.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.busapiclienttokenscompose.R
import com.example.busapiclienttokenscompose.domain.model.LoginDTO
import com.example.busapiclienttokenscompose.domain.model.RegisterDTO
import com.example.busapiclienttokenscompose.ui.common.LoadingProgressComponent
import com.example.busapiclienttokenscompose.ui.common.ShowSnackbarMessage
import com.example.busapiclienttokenscompose.utils.Constants

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    topBar: @Composable () -> Unit,
    onLoginSuccessful: (Any?) -> Unit = {},
) {
    val uiState by viewModel.state.collectAsState()

    if (uiState.isLoginOk) {
        viewModel.handleEvent(LoginContract.LoginEvent.LoginWasSuccessful)
        onLoginSuccessful(uiState.userId.toString())
    }

    LoginContent(
        state = uiState,
        introducedLoginData = {
            viewModel.handleEvent(
                LoginContract.LoginEvent.IntroducedLoginData(it)
            )
        },
        introducedRegisterData = {
            viewModel.handleEvent(
                LoginContract.LoginEvent.IntroducedRegisterData(it)
            )
        },
        registerMessageShown = {
            viewModel.handleEvent(
                LoginContract.LoginEvent.RegisterWasSuccessful
            )
        },
        login = {
            viewModel.handleEvent(
                LoginContract.LoginEvent.LoginUser
            )
        },
        //val focusManager = LocalFocusManager.current
        //focusManager.clearFocus()
        register = {
            viewModel.handleEvent(
                LoginContract.LoginEvent.RegisterUser
            )
        },
        errorShown = {
            viewModel.handleEvent(
                LoginContract.LoginEvent.ErrorDisplayed
            )
        },
        topBar = topBar
    )
}

@Composable
fun LoginContent(
    state: LoginContract.LoginState,
    introducedLoginData: (LoginDTO?) -> Unit = {},
    introducedRegisterData: (RegisterDTO) -> Unit = {},
    registerMessageShown: () -> Unit = {},
    login: () -> Unit = {},
    register: () -> Unit = {},
    errorShown: () -> Unit = {},
    topBar: @Composable () -> Unit = {},
) {
    val snackbarHostState = remember { SnackbarHostState() }

    if (state.isRegisterOk) {
        registerMessageShown()
        ShowSnackbarMessage(
            snackbarHostState = snackbarHostState,
            message = Constants.ACTIVATION_REQUIRED_ERROR,
        ) {
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = topBar,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ) {
            CustomTextField(
                value = state.userToLogin?.username ?: "",
                onValueChange = { username -> introducedLoginData(state.userToLogin?.copy(username = username)) },
                label = stringResource(R.string.username)
            )

            CustomTextField(
                value = state.userToLogin?.password ?: "",
                onValueChange = { password -> introducedLoginData(state.userToLogin?.copy(password = password)) },
                label = stringResource(R.string.password)
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
            )
            LoginRegisterButtonRow(
                onClickAction = login,
                buttonText = stringResource(id = R.string.login_upper),
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
            )
            CustomTextField(
                value = state.userToRegister?.firstName ?: "",
                onValueChange = { firstName ->
                    state.userToRegister?.copy(firstName = firstName)
                        ?.let { introducedRegisterData(it) }
                },
                label = stringResource(R.string.first_name)
            )
            CustomTextField(
                value = state.userToRegister?.lastName ?: "",
                onValueChange = { lastName ->
                    state.userToRegister?.copy(lastName = lastName)
                        ?.let { introducedRegisterData(it) }
                },
                label = stringResource(R.string.last_name)
            )
            CustomTextField(
                value = state.userToRegister?.username ?: "",
                onValueChange = { username ->
                    state.userToRegister?.copy(username = username)
                        ?.let { introducedRegisterData(it) }
                },
                label = stringResource(R.string.username)
            )
            CustomTextField(
                value = state.userToRegister?.password ?: "",
                onValueChange = { password ->
                    state.userToRegister?.copy(password = password)
                        ?.let { introducedRegisterData(it) }
                },
                label = stringResource(R.string.password)
            )
            CustomTextField(
                value = state.userToRegister?.email ?: "",
                onValueChange = { email ->
                    state.userToRegister?.copy(email = email)
                        ?.let { introducedRegisterData(it) }
                },
                label = stringResource(R.string.email)
            )
            CustomTextField(
                value = state.userToRegister?.phone ?: "",
                onValueChange = { phone ->
                    state.userToRegister?.copy(phone = phone)
                        ?.let { introducedRegisterData(it) }
                },
                label = stringResource(R.string.phone)
            )
            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
            )
            LoginRegisterButtonRow(
                onClickAction = register,
                buttonText = stringResource(id = R.string.register_upper),
            )
            ShowSnackbarMessage(
                snackbarHostState = snackbarHostState,
                message = state.error,
            ) {
                errorShown()
            }
        }
        LoadingProgressComponent(showComponent = state.isLoading)
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    )
}

@Composable
fun LoginRegisterButtonRow(
    onClickAction: () -> Unit = {},
    buttonText: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Spacer(modifier = Modifier.width(3.dp))
        Button(
            onClick = { onClickAction() },
            modifier = Modifier
                .weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
            )
        ) {
            Text(text = buttonText)
        }
        Spacer(modifier = Modifier.width(3.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LoginRegisterButtonRow(
        buttonText = "Login",
    )
}

@Preview(showBackground = true)
@Composable
fun LoginContentPreview() {
    LoginContent(
        state = LoginContract.LoginState(),
    )
}
