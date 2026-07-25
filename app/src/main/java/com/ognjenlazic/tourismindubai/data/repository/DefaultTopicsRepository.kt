package com.ognjenlazic.tourismindubai.data.repository

import com.ognjenlazic.tourismindubai.data.source.TopicsLocalDataSource
import com.ognjenlazic.tourismindubai.data.source.TopicsRemoteDataSource
import com.ognjenlazic.tourismindubai.domain.DataResult
import com.ognjenlazic.tourismindubai.domain.Origin
import com.ognjenlazic.tourismindubai.domain.TopicsError
import com.ognjenlazic.tourismindubai.domain.model.Topics
import com.ognjenlazic.tourismindubai.domain.repository.TopicsRepository
import kotlinx.coroutines.CancellationException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Offline-first coordination between the network and the local cache.
 *
 * The network is the source of truth when it is reachable, and every successful response
 * refreshes the cache. When the request fails we fall back to whatever was cached, and the
 * caller is told the data came from [Origin.CACHE] so the UI can say so. If the cache is
 * also empty there is genuinely nothing to show, and that is reported as a failure rather
 * than as an empty success — the previous implementation returned an empty response here,
 * which rendered a blank screen with no explanation.
 */
@Singleton
class DefaultTopicsRepository @Inject constructor(
    private val remote: TopicsRemoteDataSource,
    private val local: TopicsLocalDataSource
) : TopicsRepository {

    override suspend fun getTopics(): DataResult<Topics> {
        return try {
            val topics = remote.fetchTopics()
            local.writeTopics(topics)
            DataResult.Success(topics, Origin.REMOTE)
        } catch (cancellation: CancellationException) {
            // Coroutine cancellation is control flow, not a data error. Swallowing it here
            // would keep work alive after the ViewModel scope has been torn down.
            throw cancellation
        } catch (networkFailure: Exception) {
            fallBackToCache(networkFailure)
        }
    }

    private suspend fun fallBackToCache(networkFailure: Exception): DataResult<Topics> {
        val cached = try {
            local.readTopics()
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (cacheFailure: Exception) {
            return DataResult.Failure(TopicsError.Unexpected(cacheFailure))
        }

        return if (cached.isEmpty) {
            DataResult.Failure(TopicsError.NoDataAvailable)
        } else {
            DataResult.Success(cached, Origin.CACHE)
        }
    }
}
