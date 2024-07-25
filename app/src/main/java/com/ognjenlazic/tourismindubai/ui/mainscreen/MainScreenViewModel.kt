package com.ognjenlazic.tourismindubai.ui.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ognjenlazic.tourismindubai.data.repository.Repository
import com.ognjenlazic.tourismindubai.ui.components.TileData
import com.ognjenlazic.tourismindubai.ui.components.VisualData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Random
import javax.inject.Inject


@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _soundState = MutableStateFlow<List<TileData>>(emptyList())
    private val _visualState = MutableStateFlow<List<VisualData>>(emptyList())
    private val _placeState = MutableStateFlow<List<TileData>>(emptyList())
    val errorState = MutableStateFlow<String?>(null)

    val topicsState: StateFlow<TopicsState> = combine(
        _soundState, _visualState, _placeState
    ) { sounds, visuals, places ->
        TopicsState(
            sound = sounds,
            visuals = visuals,
            places = places
        )
    }.stateIn(viewModelScope, SharingStarted.Lazily, TopicsState())

    init {
        fetchTopics()
    }

    fun clearError() {
        errorState.value = null
    }

    fun fetchTopics() {
        viewModelScope.launch {
            val random = Random()
            try {
                val response = repository.getTopics()

                _soundState.value = response.Sound.map {
                    TileData(emoji = it.emoji, text = it.label, isNotificationAvailable = random.nextBoolean())
                }

                _visualState.value = response.Visuals.map {
                    VisualData(label = it.label, photo = it.photo)
                }

                _placeState.value = response.Places.map {
                    TileData(emoji = it.emoji, text = it.label, isNotificationAvailable = false)
                }
            } catch (e: Exception) {
                errorState.value = "Failed to fetch topics: ${e.message}"
            }
        }
    }
}

data class TopicsState(
    val sound: List<TileData> = emptyList(),
    val visuals: List<VisualData> = emptyList(),
    val places: List<TileData> = emptyList()
)
