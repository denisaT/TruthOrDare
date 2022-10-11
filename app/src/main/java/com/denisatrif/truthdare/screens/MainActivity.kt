package com.denisatrif.truthdare.screens

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.denisatrif.truthdare.compose.Navigation
import com.denisatrif.truthdare.ui.theme.TruthOrDareTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TruthOrDareTheme {
                Navigation()
            }
        }
    }
}