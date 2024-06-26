package com.example.busapiclienttokenscompose
import com.example.busapiclienttokenscompose.ui.navigation.Navigation
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.busapiclienttokenscompose.ui.theme.BusApiClientTokensComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BusApiClientTokensComposeTheme(
                darkTheme = false
            ) {
                Surface(color = MaterialTheme.colorScheme.background) {
                    Navigation()
                }
            }
        }
    }
}