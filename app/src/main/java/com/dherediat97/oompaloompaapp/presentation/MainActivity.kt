package com.dherediat97.oompaloompaapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.core.view.WindowCompat
import com.dherediat97.oompaloompaapp.presentation.composable.MyNavHost
import com.dherediat97.oompaloompaapp.presentation.theme.OompaLoompaAppTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            OompaLoompaAppTheme {
                Box(
                    modifier = Modifier.semantics {
                        // Allows to use testTag() for UiAutomator's resource-id.
                        // It can be enabled high in the compose hierarchy,
                        // so that it's enabled for the whole subtree
                        testTagsAsResourceId = true
                    }
                ) {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        MyNavHost()
                    }
                }
            }
        }
    }
}