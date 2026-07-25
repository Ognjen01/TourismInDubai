package com.ognjenlazic.tourismindubai.ui.mainscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.ognjenlazic.tourismindubai.R
import com.ognjenlazic.tourismindubai.ui.components.CategorySection
import com.ognjenlazic.tourismindubai.ui.components.TileData
import com.ognjenlazic.tourismindubai.ui.components.VisualData
import com.ognjenlazic.tourismindubai.ui.components.VisualsSection
import com.ognjenlazic.tourismindubai.ui.theme.ButtonBlue
import com.ognjenlazic.tourismindubai.ui.theme.Dimens
import com.ognjenlazic.tourismindubai.ui.theme.Typography

object MainScreenTestTags {
    const val LOADING = "main_screen_loading"
    const val CONTENT = "main_screen_content"
    const val OFFLINE_BANNER = "main_screen_offline_banner"
    const val ERROR_DIALOG = "main_screen_error_dialog"
}

/**
 * Stateful entry point. Collects the single UI state and forwards events.
 */
@Composable
fun MainScreen(viewModel: MainScreenViewModel) {
    val uiState by viewModel.uiState.collectAsState()

    MainScreen(
        uiState = uiState,
        onRetry = viewModel::fetchTopics,
        onDismissError = viewModel::clearError
    )
}

/**
 * Stateless rendering of [MainScreenUiState].
 *
 * Kept free of the ViewModel so it can be exercised directly from tests by handing it a
 * state value, which is what makes loading, offline and error rendering testable at all.
 */
@Composable
fun MainScreen(
    uiState: MainScreenUiState,
    onRetry: () -> Unit,
    onDismissError: () -> Unit
) {
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

            if (uiState.isShowingCachedData) {
                Text(
                    text = stringResource(id = R.string.showing_offline_content),
                    textAlign = TextAlign.Center,
                    style = Typography.titleSmall,
                    modifier = Modifier
                        .testTag(MainScreenTestTags.OFFLINE_BANNER)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .padding(Dimens.mediumPadding)
                )
            }

            if (uiState.hasContent) {
                Column(modifier = Modifier.testTag(MainScreenTestTags.CONTENT)) {
                    CategorySection(
                        title = stringResource(id = R.string.sound),
                        sectionData = uiState.topics.sounds.map { topic ->
                            TileData(
                                emoji = topic.emoji,
                                text = topic.label,
                                isNotificationAvailable = topic.emoji.equals("live", ignoreCase = true)
                            )
                        }
                    )
                    Spacer(modifier = Modifier.height(Dimens.largePadding))
                    VisualsSection(
                        title = stringResource(id = R.string.visuals),
                        visualData = uiState.topics.visuals.map { visual ->
                            VisualData(label = visual.label, photo = visual.photoUrl)
                        }
                    )
                    Spacer(modifier = Modifier.height(Dimens.largePadding))
                    CategorySection(
                        title = stringResource(id = R.string.places),
                        sectionData = uiState.topics.places.map { topic ->
                            TileData(
                                emoji = topic.emoji,
                                text = topic.label,
                                isNotificationAvailable = false
                            )
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(Dimens.bottomSpacerHeight))
        }

        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .testTag(MainScreenTestTags.LOADING)
                    .align(Alignment.Center)
            )
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

        uiState.errorMessageRes?.let { messageRes ->
            AlertDialog(
                modifier = Modifier.testTag(MainScreenTestTags.ERROR_DIALOG),
                onDismissRequest = onDismissError,
                confirmButton = {
                    Button(onClick = onRetry) {
                        Text(stringResource(id = R.string.retry))
                    }
                },
                dismissButton = {
                    TextButton(onClick = onDismissError) {
                        Text(stringResource(id = R.string.ok))
                    }
                },
                text = { Text(text = stringResource(id = messageRes)) }
            )
        }
    }
}
