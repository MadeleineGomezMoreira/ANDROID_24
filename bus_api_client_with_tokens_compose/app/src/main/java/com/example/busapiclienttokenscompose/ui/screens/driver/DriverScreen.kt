package com.example.busapiclienttokenscompose.ui.screens.driver

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DriverScreen(
    viewModel: DriverViewModel = hiltViewModel(),
    userId: Int, //we add this here and it is retrieved through the route when we navigate to this screen
    bottomNavigationBar: @Composable () -> Unit,
    topBar: @Composable () -> Unit,
    onLineClicked: () -> Unit = {},
) {

    val uiState by viewModel.state.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.handleEvent(DriverContract.DriverEvent.GetDriver(userId))
    }


    if (uiState.lineId != null) {
        viewModel.handleEvent(DriverContract.DriverEvent.LineClicked)
        onLineClicked()
    }

    DriverContent(
        state = uiState,
        onLineClicked = onLineClicked,
        topBar = topBar,
        bottomNavigationBar = bottomNavigationBar,
    )

}

@Composable
fun DriverContent(
    state: DriverContract.DriverState,
    onLineClicked: () -> Unit = {},
    topBar: @Composable () -> Unit = {},
    bottomNavigationBar: @Composable () -> Unit = {},
) {

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = topBar,
        bottomBar = bottomNavigationBar,
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            CustomTextBold(
                text = "First Name",
            )
            CustomText(
                text = state.driver?.firstName ?: "",
            )
            CustomTextBold(
                text = "Last Name",
            )
            CustomText(
                text = state.driver?.lastName ?: "",
            )
            CustomTextBold(
                text = "Phone Number",
            )
            CustomText(
                text = state.driver?.phone ?: "",
            )
            HorizontalDivider(
                modifier = Modifier.padding(top = 30.dp)
            )
            Text(
                text = "ASSIGNED LINE",
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .padding(top = 35.dp)
                    .align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp,
            )
            CustomActionButton(
                onClickAction = onLineClicked,
                buttonText = (state.lineId ?: "LINE ID").toString(),
            )
        }
    }

}
@Composable
fun CustomActionButton(
    onClickAction: () -> Unit = {},
    buttonText: String,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .width(200.dp)
                .padding(15.dp),
        ) {
            Spacer(modifier = Modifier.width(3.dp))
            Button(
                onClick = { onClickAction() },
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                )
            ) {
                Text(text = buttonText, fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.width(3.dp))
        }
    }
}

@Composable
fun CustomTextBold(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.tertiary,
        modifier = modifier
            .padding(top = 30.dp),
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Bold,
        fontSize = 35.sp
    )
}

@Composable
fun CustomText(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        modifier = modifier
            .padding(top = 35.dp),
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp
    )
}

@Preview(showBackground = true)
@Composable
fun LoginContentPreview() {
    DriverContent(
        state = DriverContract.DriverState(),
    )
}
