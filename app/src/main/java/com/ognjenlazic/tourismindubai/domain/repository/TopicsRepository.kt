package com.ognjenlazic.tourismindubai.domain.repository

import com.ognjenlazic.tourismindubai.domain.DataResult
import com.ognjenlazic.tourismindubai.domain.model.Topics

/**
 * Single entry point for topic data.
 *
 * Declared as an interface in the domain layer so the ViewModel depends on behaviour rather
 * than on Retrofit and Room, and so tests can substitute a fake without a DI container.
 */
interface TopicsRepository {
    suspend fun getTopics(): DataResult<Topics>
}
