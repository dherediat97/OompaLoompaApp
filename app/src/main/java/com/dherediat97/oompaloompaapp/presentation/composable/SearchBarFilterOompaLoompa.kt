package com.dherediat97.oompaloompaapp.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.unit.dp
import com.dherediat97.oompaloompaapp.presentation.viewmodel.list.OompaLoompaListViewModel
import org.koin.androidx.compose.koinViewModel
import java.util.Locale.filter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarFilterOompaLoompa(
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
            .padding(top = 12.dp)
            .semantics { isTraversalGroup = true }) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = -1f },
            query = query,
            onQueryChange = {
                query = it
                filterResults(byProfession, byGender, byName, query, oompaLoompaListViewModel)
                if(query.isEmpty()) onClearFilters()
            },
            onSearch = {
                active = false
                filterResults(byProfession, byGender, byName, query, oompaLoompaListViewModel)
            },
            active = active,
            onActiveChange = { active = it },
            placeholder = { Text("Search here...", color = Color.White) },
            leadingIcon = {
                if (!active)
                    Icon(Icons.Default.Search, contentDescription = "search icon")
                else
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "delete search icon",
                        modifier = Modifier.clickable {
                            if (query.isNotEmpty()) {
                                query = ""
                            } else {
                                active = false
                                byProfession = false
                                byGender = false
                                byBoth = false
                                onClearFilters()
                            }
                        })
            },
            trailingIcon = {
                if (data.isLoading)
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
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
                FilterChip(colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = if (byProfession) MaterialTheme.colorScheme.primary else Color.White
                ), selected = byProfession, onClick = {
                    byGender = false
                    byBoth = false
                    byName = false
                    byProfession = !byProfession
                    query = ""
                }, label = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = rememberVectorPainter(
                                image = Icons.Default.Factory
                            ),
                            modifier = Modifier.padding(end = 4.dp),
                            contentDescription = "oompa loompa profession filter icon",
                            tint = if (!byProfession) MaterialTheme.colorScheme.primary else Color.White
                        )
                        Text("Profession")
                    }
                })
                FilterChip(colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = if (byGender) MaterialTheme.colorScheme.primary else Color.White
                ), selected = byGender, onClick = {
                    byProfession = false
                    byBoth = false
                    byName = false
                    byGender = !byGender
                    query = ""
                }, label = {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = rememberVectorPainter(
                                image = Icons.Default.Transgender
                            ),
                            modifier = Modifier.padding(end = 4.dp),
                            contentDescription = "oompa loompa gender filter icon",
                            tint = if (!byGender) MaterialTheme.colorScheme.primary else Color.White
                        )
                        Text("Gender")
                    }
                })
                FilterChip(colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = if (byBoth) MaterialTheme.colorScheme.primary else Color.White
                ), selected = byBoth, onClick = {
                    byBoth = !byBoth
                    byName = false
                    byGender = false
                    byProfession = false
                    query = ""
                }, label = {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = rememberVectorPainter(
                                image = Icons.Default.Transgender
                            ),
                            modifier = Modifier.padding(end = 4.dp),
                            contentDescription = "oompa loompa gender filter icon",
                            tint = if (!byBoth) MaterialTheme.colorScheme.primary else Color.White
                        )
                        Icon(
                            painter = rememberVectorPainter(
                                image = Icons.Default.Factory
                            ),
                            modifier = Modifier.padding(end = 4.dp),
                            contentDescription = "oompa loompa profession filter icon",
                            tint = if (!byBoth) MaterialTheme.colorScheme.primary else Color.White
                        )
                        Text("Both")
                    }
                })
            }
            content()
        }
        Row(modifier = Modifier.padding(top = 64.dp)) {
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