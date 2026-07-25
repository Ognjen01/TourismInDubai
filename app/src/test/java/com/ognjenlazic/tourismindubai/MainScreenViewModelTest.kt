package com.ognjenlazic.tourismindubai

import com.ognjenlazic.tourismindubai.domain.DataResult
import com.ognjenlazic.tourismindubai.domain.Origin
import com.ognjenlazic.tourismindubai.domain.TopicsError
import com.ognjenlazic.tourismindubai.fake.FakeTopicsRepository
import com.ognjenlazic.tourismindubai.fake.TestData
import com.ognjenlazic.tourismindubai.ui.mainscreen.MainScreenViewModel
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

/**
 * Covers state mapping and error recovery. The ViewModel under test is a real instance —
 * only the repository is a fake.
 */
class MainScreenViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = FakeTopicsRepository()

    @Test
    fun `starts in a loading state before the first result arrives`() = runTest {
        val viewModel = MainScreenViewModel(repository)

        assertTrue(viewModel.uiState.value.isLoading)
        assertFalse(viewModel.uiState.value.hasContent)
    }

    @Test
    fun `remote success maps into content without an offline banner`() = runTest {
        repository.result = DataResult.Success(TestData.topics, Origin.REMOTE)

        val viewModel = MainScreenViewModel(repository)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(TestData.topics, state.topics)
        assertFalse(state.isShowingCachedData)
        assertNull(state.errorMessageRes)
    }

    @Test
    fun `cached success flags that the content is stale`() = runTest {
        repository.result = DataResult.Success(TestData.staleTopics, Origin.CACHE)

        val viewModel = MainScreenViewModel(repository)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state.isShowingCachedData)
        assertEquals(TestData.staleTopics, state.topics)
        assertNull(state.errorMessageRes)
    }

    @Test
    fun `failure surfaces an error message and stops loading`() = runTest {
        repository.result = DataResult.Failure(TopicsError.NoDataAvailable)

        val viewModel = MainScreenViewModel(repository)
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(R.string.error_no_data_available, state.errorMessageRes)
    }

    @Test
    fun `retrying after a failure clears the error and loads content`() = runTest {
        repository.result = DataResult.Failure(TopicsError.NoDataAvailable)
        val viewModel = MainScreenViewModel(repository)
        advanceUntilIdle()
        assertEquals(R.string.error_no_data_available, viewModel.uiState.value.errorMessageRes)

        repository.result = DataResult.Success(TestData.topics, Origin.REMOTE)
        viewModel.fetchTopics()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertNull(state.errorMessageRes)
        assertEquals(TestData.topics, state.topics)
        assertEquals(2, repository.callCount)
    }

    @Test
    fun `dismissing the error leaves the rest of the state intact`() = runTest {
        repository.result = DataResult.Success(TestData.staleTopics, Origin.CACHE)
        val viewModel = MainScreenViewModel(repository)
        advanceUntilIdle()

        viewModel.clearError()

        val state = viewModel.uiState.value
        assertNull(state.errorMessageRes)
        assertTrue(state.isShowingCachedData)
        assertEquals(TestData.staleTopics, state.topics)
    }
}
