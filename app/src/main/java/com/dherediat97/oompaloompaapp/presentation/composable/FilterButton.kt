package com.dherediat97.oompaloompaapp.presentation.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter

@Composable
fun FilterButton(){
    IconButton(onClick = {

    }) {
        Icon(
            painter = rememberVectorPainter(image = Icons.Default.Search),
            contentDescription = "search icon",
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}