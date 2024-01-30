package com.dherediat97.oompaloompaapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dherediat97.oompaloompaapp.presentation.composable.AppBarContainer
import com.dherediat97.oompaloompaapp.presentation.composable.OompaLoompaDetail
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
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "main") {
                        composable("main") {
                            AppBarContainer { paddingValues ->
                                OompaLoompaList(paddingValues) {oompaLoompaId->
                                    navController.navigate("oompaLoompa/$oompaLoompaId")
                                }
                            }
                        }
                        composable(
                            "oompaLoompa/{id}",
                            arguments = listOf(navArgument("id") {
                                type = NavType.IntType
                            })
                        ) { backStackEntry ->
                            AppBarContainer { paddingValues ->
                                OompaLoompaDetail(
                                    paddingValues,
                                    backStackEntry.arguments?.getInt("id")!!
                                )
                            }
                        }
                    }


                }
            }
        }
    }
}