package com.example.dat068_tentamina

import ExamInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Canvas
import com.example.dat068_tentamina.ui.theme.DAT068TentaminaTheme
import com.example.dat068_tentamina.ui.Overlay
import com.example.dat068_tentamina.ui.Login
import com.example.dat068_tentamina.viewmodel.TentaViewModel
import com.example.dat068_tentamina.viewmodel.ExamInfo
import com.example.dat068_tentamina.externalStorage.ExternalStorageManager

sealed class Screen {
    object Overlay : Screen()
    object Login : Screen()
    //object InfoPage : Screen()
}

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DAT068TentaminaTheme {
                val externalStorageManager = ExternalStorageManager()
                val tentaViewModel = remember { TentaViewModel() }
                val examInfo = remember {ExamInfo()}
                val recoveryMode by tentaViewModel.recoveryMode.collectAsState()

                var isDataFetched by remember { mutableStateOf(false) } // Track if data is fetched
                var currentScreen by remember { mutableStateOf<Screen>(Screen.Login) } // Start with Login

                // Setup callback for data fetching completion
                examInfo.setOnDataFetched {
                    isDataFetched = true
                    currentScreen = Screen.Overlay
                }

                when (currentScreen) {
                    Screen.Overlay -> {
                        if (isDataFetched) {
                           Overlay(
                                viewModel = tentaViewModel,
                                examInfo = examInfo,
                                activity = this,
                                recoveryMode = recoveryMode,
                            )
                        } else {
                            // Optionally show a loading screen or placeholder
                            android.graphics.Canvas()
                        }
                    }
                    Screen.Login -> Login(
                        viewModel = tentaViewModel,
                        examInfo = examInfo,
                        onNavigateToExam = { currentScreen = Screen.Overlay },
                    )
                }
            }
        }
    }
}



