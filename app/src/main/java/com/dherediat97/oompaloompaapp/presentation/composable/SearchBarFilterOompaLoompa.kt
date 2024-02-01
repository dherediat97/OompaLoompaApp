package com.dherediat97.oompaloompaapp.presentation.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Factory
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Transgender
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import com.dherediat97.oompaloompaapp.R
import com.dherediat97.oompaloompaapp.presentation.viewmodel.list.OompaLoompaListViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarFilterOompaLoompa(
    innerPadding: PaddingValues,
    oompaLoompaListViewModel: OompaLoompaListViewModel = koinViewModel(),
    content: @Composable () -> Unit,
    onClearFilters: () -> Unit
) {
    var query by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    var byName by rememberSaveable { mutableStateOf(true) }
    var byProfession by rememberSaveable { mutableStateOf(false) }
    var byGender by rememberSaveable { mutableStateOf(false) }
    var byBoth by rememberSaveable { mutableStateOf(false) }

    val data by oompaLoompaListViewModel.oompaLoompaUiState.collectAsState()

    Box(
        Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .navigationBarsPadding()
            .semantics { isTraversalGroup = true }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .paint(
                    painter = painterResource(id = R.drawable.wave_top),
                    contentScale = ContentScale.FillHeight
                )
        )
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .testTag("searchBarFilter")
                .semantics { traversalIndex = -1f },
            query = query,
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.onTertiary,
            ),
            onQueryChange = {
                query = it
                filterResults(byProfession, byGender, byName, query, oompaLoompaListViewModel)
                if (query.isEmpty()) onClearFilters()
            },
            onSearch = {
                active = false
                filterResults(byProfession, byGender, byName, query, oompaLoompaListViewModel)
            },
            active = active,
            onActiveChange = { active = it },
            placeholder = {
                Text(
                    stringResource(id = R.string.search_hint),
                    color = MaterialTheme.colorScheme.onBackground
                )
            },
            leadingIcon = {
                if (!active)
                    Icon(
                        Icons.Default.Search,
                        contentDescription = "search icon",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                else
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "delete search icon",
                        tint = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.clickable {
                            if (query.isNotEmpty()) {
                                query = ""
                            } else {
                                active = false
                                byProfession = false
                                byGender = false
                                byBoth = false
                                byName = true
                                onClearFilters()
                            }
                        })
            },
            trailingIcon = {
                if (data.isLoading)
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.background,
                        modifier = Modifier.size(26.dp)
                    )
            },
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 12.dp, end = 12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CustomFilterChip(
                    onClick = {
                        byProfession = !byProfession
                        byName = false
                        byBoth = false
                        byGender = false
                        onClearFilters()
                    },
                    chipIcon = Icons.Filled.Factory,
                    byCondition = byProfession,
                    textValue = "Profession"
                )

                CustomFilterChip(
                    onClick = {
                        byGender = !byGender
                        byName = false
                        byBoth = false
                        byProfession = false
                        onClearFilters()
                    },
                    chipIcon = Icons.Filled.Transgender,
                    byCondition = byGender,
                    textValue = "Gender"
                )

                CustomFilterTwoChip(
                    onClick = {
                        byBoth = !byBoth
                        byName = false
                        byGender = false
                        byProfession = false
                        onClearFilters()
                    },
                    chipIcon = Icons.Filled.Transgender,
                    secondaryChipIcon = Icons.Filled.Factory,
                    byCondition = byBoth,
                    textValue = "Both"
                )
            }
            Row(modifier = Modifier.padding(top = 0.dp)) {
                content()
            }
        }
        Row(modifier = Modifier
            .padding(top = innerPadding.calculateTopPadding()+38.dp)) {
            content()
        }
    }
}

fun filterResults(
    byProfession: Boolean,
    byGender: Boolean,
    byName: Boolean,
    query: String,
    oompaLoompaListViewModel: OompaLoompaListViewModel
) {
    when {
        byProfession -> oompaLoompaListViewModel.filterByProfession(query)
        byGender -> oompaLoompaListViewModel.filterByGender(query)
        byName -> oompaLoompaListViewModel.filterByName(query)
    }
}