package com.dherediat97.oompaloompaapp.presentation.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.dherediat97.oompaloompaapp.R


/**
 * App Bar that have the actions to filter or search the list
 * and the content of the screen(the list of oompa loompa or the detail)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBarContainer(
    content: @Composable (PaddingValues) -> Unit,
    backButton: @Composable () -> Unit,
    actions: @Composable () -> Unit,
    title: String = stringResource(id = R.string.app_name)
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                navigationIcon = { backButton() },
                actions = {
                    actions()
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                title = {
                    Text(title)
                }
            )
        },
    ) { innerPadding ->
        content(innerPadding)
    }
}