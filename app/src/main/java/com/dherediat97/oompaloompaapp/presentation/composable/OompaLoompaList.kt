package com.dherediat97.oompaloompaapp.presentation.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.dherediat97.oompaloompaapp.presentation.viewmodel.OompaLoompaListViewModel
import org.koin.androidx.compose.koinViewModel


/**
 * The Oompa Loompa List, in this case is grid, for the user is better display in
 * grid layout than column layout
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OompaLoompaList(
    innerPadding: PaddingValues,
    oompaLoompaListViewModel: OompaLoompaListViewModel = koinViewModel(),
    onNavigateOompaLoompa: (Int) -> Unit,
) {
    val data by oompaLoompaListViewModel.oompaLoompaUiState.collectAsState()

    //Each open execute the view model function to fetch all Oompa Loompa
    LaunchedEffect(Unit) {
        oompaLoompaListViewModel.fetchAllWorkers()
    }

    //Present a loading while
    if (data.isLoading)
        LoadingView()

    //When there are data build a list view
    LazyVerticalGrid(
        modifier = Modifier
            .padding(
                top = innerPadding.calculateTopPadding(),
                end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                start = innerPadding.calculateStartPadding(LayoutDirection.Ltr)
            )
            .fillMaxWidth()
            .padding(8.dp),
        columns = GridCells.Fixed(2),
    ) {
        items(data.oompaLoompaList, itemContent = { oompaLoompa ->
            Row(
                Modifier
                    .animateItemPlacement()
                    .clickable { onNavigateOompaLoompa(oompaLoompa.id) }) {
                OompaLoompaCard(oompaLoompa)
            }
        })
    }

}