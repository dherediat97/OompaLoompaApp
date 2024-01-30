package com.dherediat97.oompaloompaapp.presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.dherediat97.oompaloompaapp.R


@Composable
fun ErrorView() {
    Image(
        painter = painterResource(R.drawable.empty_state),
        contentDescription = "empty state chocolate"
    )
}