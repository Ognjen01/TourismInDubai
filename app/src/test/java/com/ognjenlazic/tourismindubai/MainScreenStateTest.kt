package com.ognjenlazic.tourismindubai

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.ognjenlazic.tourismindubai.fake.TestData
import com.ognjenlazic.tourismindubai.ui.mainscreen.MainScreen
import com.ognjenlazic.tourismindubai.ui.mainscreen.MainScreenTestTags
import com.ognjenlazic.tourismindubai.ui.mainscreen.MainScreenUiState
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Renders the screen against each UI state on the JVM, so loading, offline and error
 * presentation is covered in CI without an emulator.
 */
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [33])
class MainScreenStateTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun `loading state shows a progress indicator`() {
        composeRule.setContent {
            MainScreen(
                uiState = MainScreenUiState(isLoading = true),
                onRetry = {},
                onDismissError = {}
            )
        }

        composeRule.onNodeWithTag(MainScreenTestTags.LOADING).assertIsDisplayed()
    }

    @Test
    fun `content state renders sections and no progress indicator`() {
        composeRule.setContent {
            MainScreen(
                uiState = MainScreenUiState(isLoading = false, topics = TestData.topics),
                onRetry = {},
                onDismissError = {}
            )
        }

        composeRule.onNodeWithTag(MainScreenTestTags.CONTENT).assertIsDisplayed()
        composeRule.onNodeWithTag(MainScreenTestTags.LOADING).assertDoesNotExist()
    }

    @Test
    fun `cached content shows the offline banner`() {
        composeRule.setContent {
            MainScreen(
                uiState = MainScreenUiState(
                    topics = TestData.topics,
                    isShowingCachedData = true
                ),
                onRetry = {},
                onDismissError = {}
            )
        }

        composeRule.onNodeWithTag(MainScreenTestTags.OFFLINE_BANNER).assertIsDisplayed()
    }

    @Test
    fun `fresh content hides the offline banner`() {
        composeRule.setContent {
            MainScreen(
                uiState = MainScreenUiState(
                    topics = TestData.topics,
                    isShowingCachedData = false
                ),
                onRetry = {},
                onDismissError = {}
            )
        }

        composeRule.onNodeWithTag(MainScreenTestTags.OFFLINE_BANNER).assertDoesNotExist()
    }

    @Test
    fun `error state shows a dialog whose retry button calls back`() {
        var retries = 0

        composeRule.setContent {
            MainScreen(
                uiState = MainScreenUiState(
                    errorMessageRes = R.string.error_no_data_available
                ),
                onRetry = { retries++ },
                onDismissError = {}
            )
        }

        composeRule.onNodeWithTag(MainScreenTestTags.ERROR_DIALOG).assertIsDisplayed()
        composeRule.onNodeWithText("Retry").performClick()

        assertEquals(1, retries)
    }
}
