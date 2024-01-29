package com.dherediat97.oompaloompaapp.presentation.composable

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.dherediat97.oompaloompaapp.presentation.viewmodel.OompaLoompaListViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun OompaList(viewModel: OompaLoompaListViewModel = koinViewModel()) {

    LaunchedEffect(Unit) {
        viewModel.fetchAllWorkers()
    }

}