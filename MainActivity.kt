package com.example.roro_tab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier

class MainActivity : ComponentActivity() {
    private val viewModel: YardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Crossfade(targetState = viewModel.currentScreen, label = "ScreenTransition") { screen ->
                        when (screen) {
                            Screen.YARD -> YardManagementScreen(viewModel)
                            Screen.INPUT -> InputScreen(viewModel)
                            Screen.CHASSIS_DETAIL -> ChassisDetailScreen(viewModel)
                            Screen.TAG_REGISTER -> TagRegisterScreen(viewModel)
                        }
                    }
                }
            }
        }
    }
}