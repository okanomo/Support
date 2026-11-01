package com.example.yard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.yard.ui.ListScreen
import com.example.yard.ui.MenuScreen
import com.example.yard.ui.theme.YardTheme

private enum class AppScreen {
    Menu,
    List
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YardTheme {
                var currentScreen by rememberSaveable { mutableStateOf(AppScreen.Menu) }
                when (currentScreen) {
                    AppScreen.Menu -> MenuScreen(
                        onListSelected = { currentScreen = AppScreen.List }
                    )
                    AppScreen.List -> ListScreen()
                }
            }
        }
    }
}
