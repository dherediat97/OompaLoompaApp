package com.dherediat97.oompaloompaapp.presentation.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.Factory
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.imageLoader
import coil.request.ImageRequest
import com.dherediat97.oompaloompaapp.OompaLoompaApp
import com.dherediat97.oompaloompaapp.domain.dto.Gender
import com.dherediat97.oompaloompaapp.domain.dto.OompaLoompa
import com.dherediat97.oompaloompaapp.presentation.theme.Black
import org.koin.androidx.compose.get
import org.koin.compose.koinInject
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject

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
                    .size(64.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                contentDescription = "oompa loompa image"
            )
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "${oompaLoompa.firstName} ${oompaLoompa.lastName}",
                        textAlign = TextAlign.Start,
                        maxLines = 2,
                        fontSize = 18.sp,
                    )
                    Row(modifier = Modifier.fillMaxWidth()) {
                        AssistChip(
                            enabled = false,
                            onClick = {},
                            border = null,
                            label = {
                                Icon(
                                    painter = rememberVectorPainter(
                                        if (oompaLoompa.gender == Gender.M) Icons.Default.Male else Icons.Default.Female
                                    ),
                                    contentDescription = "gender icon oompa loompa",
                                    modifier = Modifier.size(24.dp),
                                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                                )
                            })
                    }
                }

                Text(
                    "Profession: ${oompaLoompa.profession}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 16.dp)
                )

            }

        }
    }
}