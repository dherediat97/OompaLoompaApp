package com.dherediat97.oompaloompaapp.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp

@Composable
fun CustomFilterTwoChip(
    onClick: () -> Unit,
    chipIcon: ImageVector,
    secondaryChipIcon: ImageVector? = null,
    byCondition: Boolean,
    textValue: String
) {
    FilterChip(colors = FilterChipDefaults.filterChipColors(
        selectedContainerColor = if (byCondition) MaterialTheme.colorScheme.primary else Color.White
    ), selected = byCondition, onClick = {
        onClick()
    }, label = {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = rememberVectorPainter(image = chipIcon),
                modifier = Modifier.padding(end = 4.dp),
                contentDescription = "oompa loompa custom filter icon",
                tint = if (!byCondition) MaterialTheme.colorScheme.primary else Color.White
            )
            if (secondaryChipIcon != null)
                Icon(
                    painter = rememberVectorPainter(
                        image = secondaryChipIcon
                    ),
                    modifier = Modifier.padding(end = 4.dp),
                    contentDescription = "oompa loompa custom filter icon",
                    tint = if (!byCondition) MaterialTheme.colorScheme.primary else Color.White
                )
            Text(
                textValue,
                color = if (byCondition) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onBackground
            )
        }
    })
}

@Composable
fun CustomFilterChip(
    onClick: () -> Unit,
    chipIcon: ImageVector,
    byCondition: Boolean,
    textValue: String
) {
    CustomFilterTwoChip(
        onClick = onClick,
        chipIcon = chipIcon,
        secondaryChipIcon = null,
        byCondition = byCondition,
        textValue = textValue
    )
}