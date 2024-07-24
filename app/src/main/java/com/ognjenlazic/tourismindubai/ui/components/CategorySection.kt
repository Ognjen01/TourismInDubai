package com.ognjenlazic.tourismindubai.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CategorySection(
    title: String,
    sectionData: List<TileData>
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            state = rememberLazyGridState()
        ) {
            items(sectionData.size) { index ->
                CategorySectionTile(
                    emoji = sectionData[index].emoji,
                    text = sectionData[index].text,
                    isNotificationAvailable = sectionData[index].notification,
                    onClick = { }
                )
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
