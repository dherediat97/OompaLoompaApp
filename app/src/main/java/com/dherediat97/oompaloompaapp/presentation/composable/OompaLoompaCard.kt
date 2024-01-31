package com.dherediat97.oompaloompaapp.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dherediat97.oompaloompaapp.domain.dto.OompaLoompa

@Composable
fun OompaLoompaCard(oompaLoompa: OompaLoompa) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(oompaLoompa.image)
                    .crossfade(true)
                    .build(),
                modifier = Modifier
                    .size(82.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                contentDescription = "oompa loompa image"
            )
            Column(modifier = Modifier.fillMaxSize()) {
                Text(
                    "${oompaLoompa.firstName} ${oompaLoompa.lastName}",
                    textAlign = TextAlign.Start,
                    maxLines = 2,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(start = 12.dp)
                )
                Text(
                    "Profession: ${oompaLoompa.profession}",
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(start = 12.dp)
                )
                Text(
                    "Gender: ${oompaLoompa.gender.value}",
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(start = 12.dp)
                )
            }

        }

    }
}