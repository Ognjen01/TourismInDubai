package com.ognjenlazic.tourismindubai.ui.mainscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ognjenlazic.tourismindubai.ui.components.CategorySection
import com.ognjenlazic.tourismindubai.ui.components.TileData
import com.ognjenlazic.tourismindubai.ui.components.VisualsSection

@Composable
fun MainScreen(viewModel: MainScreenViewModel) {
    val topicsState by viewModel.topicsState.collectAsState()
    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            Text(
                text = "What are topics you enjoy talking about",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "topics will appear on your profile and help you find interesting places",
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            topicsState.let { topics ->
                CategorySection(
                    title = "Sound",
                    sectionData = topics.sound.map {
                        TileData(emoji = it.emoji, text = it.text, isNotificationAvailable = false)
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                VisualsSection(
                    title = "Visuals",
                    visualData = topics.visuals
                )

                Spacer(modifier = Modifier.height(16.dp))

                CategorySection(
                    title = "Places",
                    sectionData = topics.places.map {
                        TileData(emoji = it.emoji, text = it.text , isNotificationAvailable = false)
                    }
                )
            }

            Spacer(modifier = Modifier.height(80.dp))
        }

        Button(
            onClick = { /* Handle next action */ },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(text = "next", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

