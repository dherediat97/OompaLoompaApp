package com.dherediat97.oompaloompaapp.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Factory
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.dherediat97.oompaloompaapp.data.dto.Favorite
import com.dherediat97.oompaloompaapp.data.dto.OompaLoompa


val mockOompaLoompa = OompaLoompa(
    id = 1,
    firstName = "Marcy",
    lastName = "Karadzas",
    image = "https://s3.eu-central-1.amazonaws.com/napptilus/level-test/1.jpg",
    profession = "Developer",
    email = "",
    age = 21,
    country = "Loompalandia",
    height = 99,
    gender = "F",
    favorite = Favorite("white", "fried chips", "")
)

@Composable
@Preview
fun OompaLoompaDetail() {
    val data = mockOompaLoompa

    Column(modifier = Modifier
        .fillMaxSize()) {
        AsyncImage(
            model = data.image,
            contentDescription = "oompa loompa image",
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            "${data.firstName} ${data.lastName}",
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
            fontSize = 32.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary
        )

        OompaLoompaDetailBox(
            if (data.gender == "m") Icons.Default.Male else Icons.Default.Female,
            "Gender",
            if (data.gender == "m") "Male" else "Female"
        )


        OompaLoompaDetailBox(Icons.Default.Map, "Country", data.country)
        OompaLoompaDetailBox(Icons.Default.CalendarMonth, "Age", data.age.toString())
        OompaLoompaDetailBox(Icons.Default.ColorLens, "Favourite Color", data.favorite.color)
        OompaLoompaDetailBox(Icons.Default.Fastfood, "Favourite Food", data.favorite.food)
        OompaLoompaDetailBox(Icons.Default.Factory, "Profession", data.profession)
    }
}

@Composable
fun OompaLoompaDetailBox(
    oompaLoompaKey: ImageVector,
    oompaLoompaLabel: String,
    oompaLoompaValue: String
) {
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
                .padding(end = 8.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "$oompaLoompaLabel: ",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            oompaLoompaValue.capitalize(),
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }

}