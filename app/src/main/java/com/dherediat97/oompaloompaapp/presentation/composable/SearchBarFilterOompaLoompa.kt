package com.dherediat97.oompaloompaapp.presentation.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Badge
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SelectableChipColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
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
import com.dherediat97.oompaloompaapp.domain.dto.OompaLoompa
import com.dherediat97.oompaloompaapp.presentation.viewmodel.list.OompaLoompaListViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarFilterOompaLoompa(
    oompaLoompaListViewModel: OompaLoompaListViewModel = koinViewModel(),
    content: @Composable (List<OompaLoompa>) -> Unit
) {
    var query by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    var byProfession by rememberSaveable { mutableStateOf(false) }
    var byGender by rememberSaveable { mutableStateOf(false) }

    val data by oompaLoompaListViewModel.oompaLoompaUiState.collectAsState()

    Box(
        Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .fillMaxWidth()
            .semantics { isTraversalGroup = true }) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = -1f },
            query = query,
            onQueryChange = {
                query = it
                when {
                    byProfession && !byGender -> oompaLoompaListViewModel.filterByProfession(query)
                    byGender && !byProfession -> oompaLoompaListViewModel.filterByGender(query)
//                    byProfession && byGender -> oompaLoompaListViewModel.filterByGenderAndProfession(query)
                    query.isNotEmpty() -> oompaLoompaListViewModel.filterByName(query)
                }
            },
            onSearch = {
                active = false
            },
            active = active,
            onActiveChange = {
                active = it
            },
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
                    byProfession = !byProfession
                }, label = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = rememberVectorPainter(
                                image = Icons.Default.Factory
                            ),
                            modifier = Modifier.padding(end = 8.dp),
                            contentDescription = "oompa loompa profession filter icon",
                            tint = if (!byProfession) MaterialTheme.colorScheme.primary else Color.White
                        )
                        Text("By profession")
                    }
                })
                FilterChip(colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = if (byGender) MaterialTheme.colorScheme.primary else Color.White
                ), selected = byGender, onClick = {
                    byGender = !byGender
                }, label = {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = rememberVectorPainter(
                                image = Icons.Default.Transgender
                            ),
                            modifier = Modifier.padding(end = 8.dp),
                            contentDescription = "oompa loompa gender filter icon",
                            tint = if (!byGender) MaterialTheme.colorScheme.primary else Color.White
                        )
                        Text("By gender")
                    }
                })
            }
            content(data.oompaLoompaListFiltered)
        }
        Row(modifier = Modifier.padding(top = 64.dp)) {
            content(data.oompaLoompaList)
        }
    }
}