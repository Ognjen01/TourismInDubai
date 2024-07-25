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
import androidx.compose.ui.text.style.TextAlign
import com.ognjenlazic.tourismindubai.ui.components.CategorySection
import com.ognjenlazic.tourismindubai.ui.components.VisualsSection
import com.ognjenlazic.tourismindubai.ui.theme.ButtonBlue
import com.ognjenlazic.tourismindubai.R
import com.ognjenlazic.tourismindubai.ui.theme.Dimens
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
                    .padding(Dimens.topSectionPadding)
                    .fillMaxSize()
            ) {
                Text(
                    text = stringResource(id = R.string.what_are_topics_you_enjoy),
                    textAlign = TextAlign.Center,
                    style = Typography.titleLarge,
                    modifier = Modifier.padding(bottom = Dimens.mediumPadding)
                )

                Text(
                    text = stringResource(id = R.string.topics_will_appear),
                    textAlign = TextAlign.Center,
                    style = Typography.titleSmall,
                    modifier = Modifier.padding(bottom = Dimens.mediumPadding)
                )
            }

            topicsState.let { topics ->
                CategorySection(
                    title = stringResource(id = R.string.sound),
                    sectionData = topics.sound
                )
                Spacer(modifier = Modifier.height(Dimens.largePadding))
                VisualsSection(
                    title = stringResource(id = R.string.visuals),
                    visualData = topics.visuals
                )
                Spacer(modifier = Modifier.height(Dimens.largePadding))
                CategorySection(
                    title = stringResource(id = R.string.places),
                    sectionData = topics.places
                )
            }
            Spacer(modifier = Modifier.height(Dimens.bottomSpacerHeight))
        }
        Button(
            onClick = { /* Handle next action */ },
            shape = RoundedCornerShape(25),
            colors = ButtonDefaults.buttonColors(containerColor = ButtonBlue),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(Dimens.largePadding)
                .width(Dimens.buttonWidth)
                .height(Dimens.buttonHeight)
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

