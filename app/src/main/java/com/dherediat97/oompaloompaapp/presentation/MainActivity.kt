package com.dherediat97.oompaloompaapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dherediat97.oompaloompaapp.presentation.composable.AppBarContainer
import com.dherediat97.oompaloompaapp.presentation.composable.FilterButton
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
                    NavHost(
                        navController = navController,
                        startDestination = "main",
                    ) {
                        composable("main") {
                            AppBarContainer(
                                backButton = {},
                                withFilters = { FilterButton() },
                                content = { paddingValues ->
                                    OompaLoompaList(paddingValues) { oompaLoompaId ->
                                        navController.navigate("oompaLoompa/$oompaLoompaId")
                                    }
                                })
                        }

                        //Composable of Detail Oompa Loompa Worker
                        composable(
                            "oompaLoompa/{id}",
                            arguments = listOf(navArgument("id") {
                                type = NavType.IntType
                            })
                        ) { backStackEntry ->
                            AppBarContainer(backButton = {
                                IconButton(onClick = {
                                    navController.navigateUp()
                                }) {
                                    Icon(
                                        painter = rememberVectorPainter(image = Icons.AutoMirrored.Filled.ArrowBack),
                                        contentDescription = "back button icon",
                                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }, withFilters = {}, content = { paddingValues ->
                                OompaLoompaDetail(
                                    paddingValues,
                                    backStackEntry.arguments?.getInt("id")!!
                                )
                            })
                        }
                    }


                }
            }
        }
    }
}