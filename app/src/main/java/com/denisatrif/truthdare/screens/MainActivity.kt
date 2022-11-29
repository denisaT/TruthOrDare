package com.denisatrif.truthdare.screens

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.denisatrif.truthdare.compose.Navigation
import com.denisatrif.truthdare.ui.theme.TruthOrDareTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            TruthOrDareTheme {
                Navigation()
            }
        }
    }
}