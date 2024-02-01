package com.dherediat97.oompaloompaapp.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Factory
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Height
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dherediat97.oompaloompaapp.R
import com.dherediat97.oompaloompaapp.domain.dto.ConnectionState
import com.dherediat97.oompaloompaapp.domain.dto.Gender
import com.dherediat97.oompaloompaapp.presentation.base.connectivityState
import com.dherediat97.oompaloompaapp.presentation.viewmodel.detail.OompaLoompaDetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun OompaLoompaDetail(
    innerPadding: PaddingValues,
    oompaLoompaId: Int,
    oompaLoompaDetailViewModel: OompaLoompaDetailViewModel = koinViewModel(),
) {
    val data by oompaLoompaDetailViewModel.singleOompaLoompaUiState.collectAsState()

    val connection by connectivityState()

    val isConnected = connection === ConnectionState.Available

    if (isConnected) {
        LaunchedEffect(Unit) {
            oompaLoompaDetailViewModel.fetchSingleOompaLoompa(oompaLoompaId)
        }
    } else {
        ErrorView(innerPadding) {
            oompaLoompaDetailViewModel.fetchSingleOompaLoompa(oompaLoompaId)
        }
    }

    if (data.isLoading)
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimaryContainer)
        }

    val oompaLoompa = data.oompaLoompaWorker

    if (oompaLoompa != null) {
        Column(
            modifier = Modifier
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                    start = innerPadding.calculateStartPadding(LayoutDirection.Ltr)
                )
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, start = 8.dp, end = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = oompaLoompa.image,
                    contentDescription = "oompa loompa image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape),
                )
                Text(
                    "${oompaLoompa.firstName} ${oompaLoompa.lastName}",
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .fillMaxWidth()
                        .testTag("oompaLoompaName"),
                    fontSize = 30.sp,
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize(0.80f)
                    .verticalScroll(rememberScrollState())
            ) {
                OompaLoompaDetailBox(
                    if (oompaLoompa.gender == Gender.M) Icons.Default.Male else Icons.Default.Female,
                    "Gender",
                    oompaLoompa.gender.value
                )
                OompaLoompaDetailBox(
                    Icons.Default.Map,
                    "Country",
                    oompaLoompa.country
                )
                OompaLoompaDetailBox(
                    Icons.Default.Height,
                    "Height",
                    "${oompaLoompa.height} cm"
                )
                OompaLoompaDetailBox(
                    Icons.Default.CalendarMonth,
                    "Age",
                    "${oompaLoompa.age} years"
                )
                OompaLoompaDetailBox(
                    Icons.Default.ColorLens,
                    "Favourite Color",
                    oompaLoompa.favorite.color
                )
                OompaLoompaDetailBox(
                    Icons.Default.Fastfood,
                    "Favourite Food",
                    oompaLoompa.favorite.food
                )
                OompaLoompaDetailBox(
                    Icons.Default.Factory,
                    "Profession",
                    oompaLoompa.profession
                )
            }
            Row(
                modifier = Modifier
                    .weight(1f, false),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
                        .paint(
                            painter = painterResource(id = R.drawable.wave_bottom),
                            contentScale = ContentScale.FillBounds
                        )
                )
            }

        }
    }
}