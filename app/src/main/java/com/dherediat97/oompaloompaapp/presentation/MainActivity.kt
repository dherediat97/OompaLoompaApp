package com.dherediat97.oompaloompaapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.dherediat97.oompaloompaapp.presentation.composable.AppBarContainer
import com.dherediat97.oompaloompaapp.presentation.composable.OompaLoompaList
import com.dherediat97.oompaloompaapp.presentation.theme.OompaLoompaAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            OompaLoompaAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppBarContainer { paddingValues ->
                        OompaLoompaList(paddingValues)
                    }
                }
            }
        }
    }
}