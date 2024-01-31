package com.dherediat97.oompaloompaapp.presentation.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.dherediat97.oompaloompaapp.domain.dto.ConnectionState
import com.dherediat97.oompaloompaapp.domain.dto.OompaLoompa
import com.dherediat97.oompaloompaapp.presentation.base.connectivityState
import com.dherediat97.oompaloompaapp.presentation.viewmodel.list.OompaLoompaListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.compose.koinViewModel


/**
 * The Oompa Loompa List, in this case is grid, for the user is better display in
 * grid layout than column layout
 */
@OptIn(ExperimentalFoundationApi::class, ExperimentalCoroutinesApi::class)
@Composable
fun OompaLoompaList(
    innerPadding: PaddingValues,
    oompaLoompaListViewModel: OompaLoompaListViewModel = koinViewModel(),
    dataList: List<OompaLoompa>,
    onNavigateOompaLoompa: (Int) -> Unit,
) {
    //Connectivity State
    val connection by connectivityState()
    //Boolean flag that control in real time the connection state
    val isConnected = connection === ConnectionState.Available

    //UiState of view model
    val data by oompaLoompaListViewModel.oompaLoompaUiState.collectAsState()

    val lazyGridState = rememberLazyListState()

    //Solution to check the end of the list
    val isAtBottom by remember {
        derivedStateOf {
            val layoutInfo = lazyGridState.layoutInfo
            val visibleItemsInfo = layoutInfo.visibleItemsInfo
            visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
        }
    }
    if (data.hasError) {
        ErrorView(innerPadding) {
            oompaLoompaListViewModel.fetchAllWorkers()
        }
    }

    if (isConnected) {
        //Each time the user reach the bottom of the list, fetch results of the next page
        LaunchedEffect(isAtBottom) {
            oompaLoompaListViewModel.fetchAllWorkers()
        }
    }
    //When there are data build a list view
    LazyColumn(
        state = lazyGridState,
        modifier = Modifier
            .padding(
                end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                start = innerPadding.calculateStartPadding(LayoutDirection.Ltr)
            )
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        items(dataList.distinct(), itemContent = { oompaLoompa ->
            Row(
                Modifier
                    .animateItemPlacement()
                    .clickable { onNavigateOompaLoompa(oompaLoompa.id) }) {
                OompaLoompaCard(oompaLoompa)
            }
        })
    }
}