package com.dherediat97.oompaloompaapp.presentation.composable

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun MyNavHost(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "splash",
    ) {
        composable("splash") {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                SplashView(
                    modifier = Modifier
                        .size(400.dp)
                        .align(Alignment.Center)
                ) {
                    navController.navigate("main") {
                        popUpTo("splash") {
                            inclusive = true
                        }
                    }
                }
            }
        }

        //Composable of Oompa Loompa List
        composable(
            "main",
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(700)
                )
            },
        ) {
            AppBarContainer(
                backButton = {},
                withFilters = { FilterButton() },
                content = { paddingValues ->
                    OompaLoompaList(paddingValues) { oompaLoompaId ->
                        navController.navigate("oompaLoompa/$oompaLoompaId")
                    }
                })
        }

        //Composable of Detail Oompa Loompa
        composable(
            "oompaLoompa/{id}",
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
            }),
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            },
            popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(700)
                )
            },
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