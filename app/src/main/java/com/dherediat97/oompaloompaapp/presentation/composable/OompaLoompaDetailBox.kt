package com.dherediat97.oompaloompaapp.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

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
            .padding(start = 16.dp, bottom = 8.dp, top = 8.dp)
            .fillMaxWidth()
            .padding(4.dp),
    ) {
        Icon(
            painter = rememberVectorPainter(
                image = oompaLoompaKey
            ),
            contentDescription = "oompa loompa icon",
            modifier = Modifier
                .size(40.dp)
                .padding(end = 10.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = "$oompaLoompaLabel: ",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            oompaLoompaValue.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() },
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.testTag(
                "oompaLoompa${
                    oompaLoompaLabel.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase() else it.toString()
                    }
                }"
            )
        )
    }
}