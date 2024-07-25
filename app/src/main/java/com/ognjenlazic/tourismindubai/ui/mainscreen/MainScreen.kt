package com.ognjenlazic.tourismindubai.ui.mainscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ognjenlazic.tourismindubai.ui.components.CategorySection
import com.ognjenlazic.tourismindubai.ui.components.VisualsSection
import com.ognjenlazic.tourismindubai.ui.theme.ButtonBlue

@Composable
fun MainScreen(viewModel: MainScreenViewModel) {
    val topicsState by viewModel.topicsState.collectAsState()
    val errorState by viewModel.errorState.collectAsState()

    val scrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(30.dp)
                    .fillMaxSize()
            ) {
                Text(
                    text = "What are topics you enjoy talking about",
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "topics will appear on your profile and help you find interesting places",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            topicsState.let { topics ->
                CategorySection(
                    title = "Sound",
                    sectionData = topics.sound
                )
                Spacer(modifier = Modifier.height(16.dp))
                VisualsSection(
                    title = "Visuals",
                    visualData = topics.visuals
                )
                Spacer(modifier = Modifier.height(16.dp))
                CategorySection(
                    title = "Places",
                    sectionData = topics.places
                )
            }
            Spacer(modifier = Modifier.height(80.dp))
        }
        Button(
            onClick = { /* Handle next action */ },
            shape = RoundedCornerShape(25),
            colors = ButtonDefaults.buttonColors(containerColor = ButtonBlue),
                    modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .width(300.dp)
                .height(60.dp)
        ) {
            Text(text = "next", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }

        errorState?.let { errorMessage ->
            AlertDialog(
                onDismissRequest = { viewModel.clearError() },
                confirmButton = {
                    Button(onClick = { viewModel.clearError() }) {
                        Text("OK")
                    }
                },
                text = { Text(text = errorMessage) }
            )
        }
    }
}

