package com.dherediat97.oompaloompaapp.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColor
import coil.compose.AsyncImage
import com.dherediat97.oompaloompaapp.data.dto.ConnectionState
import com.dherediat97.oompaloompaapp.data.dto.OompaLoompa
import com.dherediat97.oompaloompaapp.presentation.base.connectivityState
import com.dherediat97.oompaloompaapp.presentation.viewmodel.OompaLoompaDetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.compose.koinViewModel
import java.util.Locale


@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun OompaLoompaDetail(
    innerPadding: PaddingValues,
    oompaLoompaId: Int,
    viewModel: OompaLoompaDetailViewModel = koinViewModel(),
) {
    val data by viewModel.singleOompaLoompaUiState.collectAsState()

    val connection by connectivityState()

    val isConnected = connection === ConnectionState.Available

    if (isConnected) {
        LaunchedEffect(Unit) {
            viewModel.fetchSingleOompaLoompa(oompaLoompaId)
        }
    } else ErrorView()

    if (data.isLoading)
        LoadingView()

    val oompaLoompa = data.oompaLoompa

    if (oompaLoompa != null) {
        Column(
            modifier = Modifier
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                    start = innerPadding.calculateStartPadding(LayoutDirection.Ltr)
                )
                .fillMaxSize()
        ) {
            AsyncImage(
                model = oompaLoompa.image,
                contentDescription = "oompa loompa image",
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                "${oompaLoompa.firstName} ${oompaLoompa.lastName}",
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                fontSize = 32.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .weight(1f, fill = false)
            ) {
                with(oompaLoompa.favorite.color) {
                    OompaLoompaDetailBox(
                        if (oompaLoompa.gender == "m") Icons.Default.Male else Icons.Default.Female,
                        "Gender",
                        if (oompaLoompa.gender == "m") "Male" else "Female",
                        this@with
                    )
                    OompaLoompaDetailBox(
                        Icons.Default.Map,
                        "Country",
                        oompaLoompa.country,
                        this@with
                    )
                    OompaLoompaDetailBox(
                        Icons.Default.Height,
                        "Height",
                        "${oompaLoompa.height} cm",
                        this@with
                    )
                    OompaLoompaDetailBox(
                        Icons.Default.CalendarMonth,
                        "Age",
                        "${oompaLoompa.age} years",
                        this@with
                    )
                    OompaLoompaDetailBox(
                        Icons.Default.ColorLens,
                        "Favourite Color",
                        oompaLoompa.favorite.color,
                        this@with
                    )
                    OompaLoompaDetailBox(
                        Icons.Default.Fastfood,
                        "Favourite Food",
                        oompaLoompa.favorite.food,
                        this@with
                    )
                    OompaLoompaDetailBox(
                        Icons.Default.Factory,
                        "Profession",
                        oompaLoompa.profession,
                        this@with
                    )
                }
            }
        }
    }
}

@Composable
fun OompaLoompaDetailBox(
    oompaLoompaKey: ImageVector,
    oompaLoompaLabel: String,
    oompaLoompaValue: String,
    oompaLoompaColor: String,
) {
    var oompaLoompaCustomIconColor: Color
    val colorId: Int = LocalContext.current.resources.getIdentifier(
        oompaLoompaColor,
        "color",
        LocalContext.current.packageName
    )
    val desiredColor: Int = LocalContext.current.resources.getColor(colorId)
    oompaLoompaCustomIconColor = Color(desiredColor)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .padding(6.dp),
    ) {
        Icon(
            painter = rememberVectorPainter(
                image = oompaLoompaKey
            ),
            contentDescription = "oompa loompa icon",
            modifier = Modifier
                .size(40.dp, 40.dp)
                .padding(end = 12.dp),
            tint = oompaLoompaCustomIconColor
        )
        Text(
            text = "$oompaLoompaLabel: ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            oompaLoompaValue.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }

}