package com.denisatrif.truthdare.screens

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.denisatrif.truthdare.compose.NavGraphs
import com.denisatrif.truthdare.ui.theme.TruthOrDareTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            TruthOrDareTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}