package com.ognjenlazic.tourismindubai.ui.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ognjenlazic.tourismindubai.R
import com.ognjenlazic.tourismindubai.domain.DataResult
import com.ognjenlazic.tourismindubai.domain.Origin
import com.ognjenlazic.tourismindubai.domain.TopicsError
import com.ognjenlazic.tourismindubai.domain.repository.TopicsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: TopicsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainScreenUiState(isLoading = true))
    val uiState: StateFlow<MainScreenUiState> = _uiState.asStateFlow()

    init {
        fetchTopics()
    }

    fun fetchTopics() {
        _uiState.update { it.copy(isLoading = true, errorMessageRes = null) }

        viewModelScope.launch {
            when (val result = repository.getTopics()) {
                is DataResult.Success -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        topics = result.data,
                        isShowingCachedData = result.origin == Origin.CACHE,
                        errorMessageRes = null
                    )
                }

                is DataResult.Failure -> _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessageRes = result.error.toMessageRes()
                    )
                }
            }
        }
    }

    fun clearError() {
        _uiState.update { it.copy(errorMessageRes = null) }
    }

    private fun TopicsError.toMessageRes(): Int = when (this) {
        TopicsError.NoDataAvailable -> R.string.error_no_data_available
        is TopicsError.Unexpected -> R.string.error_unexpected
    }
}
