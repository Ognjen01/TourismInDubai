package com.ognjenlazic.tourismindubai

import app.cash.turbine.test
import com.ognjenlazic.tourismindubai.data.model.CategoryItem
import com.ognjenlazic.tourismindubai.data.model.TopicsResponse
import com.ognjenlazic.tourismindubai.data.model.VisualItem
import com.ognjenlazic.tourismindubai.data.repository.Repository
import com.ognjenlazic.tourismindubai.ui.mainscreen.MainScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class MainScreenViewModelTest {

    private lateinit var repository: Repository
    private lateinit var viewModel: MainScreenViewModel

    @Before
    fun setUp() {
        repository = mock(Repository::class.java)
        viewModel = mock(MainScreenViewModel::class.java)
    }

    private val scope = CoroutineScope(StandardTestDispatcher())

    @Test
    fun testFetchTopicsSuccess() {
        scope.launch {
            val soundData = listOf(CategoryItem(emoji = "🎵", label = "Music"))
            val visualData = listOf(VisualItem(label = "Art", photo = "photo_url"))
            val placeData = listOf(CategoryItem(emoji = "🏖️", label = "Beach"))

            val response =
                TopicsResponse(Sound = soundData, Visuals = visualData, Places = placeData)
            `when`(repository.getTopics()).thenReturn(response)

            viewModel.fetchTopics()

            viewModel.topicsState.collect {
                assert(it.sound == soundData)
                assert(it.visuals == visualData)
                assert(it.places == placeData)
            }
        }
    }

    @Test
    fun testFetchTopicsError() {
        scope.launch {
            val errorMessage = "Network error"
            `when`(repository.getTopics()).thenThrow(RuntimeException(errorMessage))

            viewModel.fetchTopics()

            viewModel.errorState.test {
                val item = awaitItem()
                assert(item == "Failed to fetch topics: $errorMessage")
            }
        }
    }

    @Test
    fun testClearError() {
        scope.launch {
            viewModel.errorState.value = "Some error"

            viewModel.clearError()

            viewModel.errorState.test {
                val item = awaitItem()
                assert(item == null)
            }
        }
    }
}
