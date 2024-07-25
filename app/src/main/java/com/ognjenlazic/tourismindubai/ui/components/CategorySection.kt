package com.ognjenlazic.tourismindubai.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CategorySection(
    title: String,
    sectionData: List<TileData>
) {
    val halfSize = (sectionData.size + 1) / 2
    val firstHalf = sectionData.take(halfSize)
    val secondHalf = sectionData.drop(halfSize)

    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(bottom = 8.dp)
        ) {
            Column {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    firstHalf.forEach { item ->
                        CategorySectionTile(
                            emoji = item.emoji,
                            text = item.text,
                            isNotificationAvailable = item.isNotificationAvailable,
                            onClick = { }
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    secondHalf.forEach { item ->
                        CategorySectionTile(
                            emoji = item.emoji,
                            text = item.text,
                            isNotificationAvailable = item.isNotificationAvailable,
                            onClick = { }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CategorySectionPreview(){

    val soundOptions = listOf(
        TileData("🦜", "BIRDS", false),
        TileData("🌊", "OCEAN", true),
        TileData("🎧", "LIVE", true),
        TileData("🔥", "FIRE", false),
        TileData("🤫", "SILENCE", false)
    )

    CategorySection(
        title = "Sound",
        sectionData = soundOptions
    )
}
