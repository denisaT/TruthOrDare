package com.denisatrif.truthdare.screens

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.denisatrif.truthdare.compose.NavGraphs
import com.denisatrif.truthdare.ui.theme.TruthOrDareTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        installSplashScreen()
        setContent {
            TruthOrDareTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}
