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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ognjenlazic.tourismindubai.ui.components.CategorySection
import com.ognjenlazic.tourismindubai.ui.components.VisualsSection
import com.ognjenlazic.tourismindubai.ui.theme.ButtonBlue
import com.ognjenlazic.tourismindubai.R
import com.ognjenlazic.tourismindubai.ui.theme.Typography

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
                    text = stringResource(id = R.string.what_are_topics_you_enjoy),
                    textAlign = TextAlign.Center,
                    style = Typography.titleLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = stringResource(id = R.string.topics_will_appear),
                    textAlign = TextAlign.Center,
                    style = Typography.titleSmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            topicsState.let { topics ->
                CategorySection(
                    title = stringResource(id = R.string.sound),
                    sectionData = topics.sound
                )
                Spacer(modifier = Modifier.height(16.dp))
                VisualsSection(
                    title = stringResource(id = R.string.visuals),
                    visualData = topics.visuals
                )
                Spacer(modifier = Modifier.height(16.dp))
                CategorySection(
                    title = stringResource(id = R.string.places),
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
            Text(
                text = stringResource(id = R.string.next),
                style = Typography.titleLarge,
                color = Color.White
            )
        }

        errorState?.let { errorMessage ->
            AlertDialog(
                onDismissRequest = { viewModel.clearError() },
                confirmButton = {
                    Button(onClick = { viewModel.clearError() }) {
                        Text(stringResource(id = R.string.ok))
                    }
                },
                text = { Text(text = errorMessage) }
            )
        }
    }
}

