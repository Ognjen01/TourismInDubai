package com.ognjenlazic.tourismindubai.ui.mainscreen

import androidx.annotation.StringRes
import com.ognjenlazic.tourismindubai.domain.model.Topics

/**
 * Everything the main screen needs to render, in one immutable value.
 *
 * The screen previously read three separate flows plus a mutable error flow, which meant
 * there were combinations of those flows that no real state of the app corresponded to.
 * One state object makes the legal states explicit and the screen a pure function of it.
 *
 * The error is carried as a string resource id rather than a formatted message so that the
 * ViewModel stays free of presentation strings and the app remains translatable.
 */
data class MainScreenUiState(
    val isLoading: Boolean = false,
    val topics: Topics = Topics.EMPTY,
    val isShowingCachedData: Boolean = false,
    @StringRes val errorMessageRes: Int? = null
) {
    val hasContent: Boolean get() = !topics.isEmpty
}
