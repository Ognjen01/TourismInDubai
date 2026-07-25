package com.ognjenlazic.tourismindubai

import com.ognjenlazic.tourismindubai.data.repository.DefaultTopicsRepository
import com.ognjenlazic.tourismindubai.domain.DataResult
import com.ognjenlazic.tourismindubai.domain.Origin
import com.ognjenlazic.tourismindubai.domain.TopicsError
import com.ognjenlazic.tourismindubai.fake.FakeLocalDataSource
import com.ognjenlazic.tourismindubai.fake.FakeRemoteDataSource
import com.ognjenlazic.tourismindubai.fake.TestData
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException

/**
 * Covers the offline-first contract: what the app shows when the network works, when it
 * fails but something was cached, and when it fails with an empty cache.
 */
class DefaultTopicsRepositoryTest {

    private val remote = FakeRemoteDataSource()
    private val local = FakeLocalDataSource()
    private val repository = DefaultTopicsRepository(remote, local)

    @Test
    fun `remote success is returned and written to the cache`() = runTest {
        remote.succeedWith(TestData.topics)

        val result = repository.getTopics()

        assertTrue(result is DataResult.Success)
        result as DataResult.Success
        assertEquals(TestData.topics, result.data)
        assertEquals(Origin.REMOTE, result.origin)
        assertEquals(listOf(TestData.topics), local.writes)
    }

    @Test
    fun `network failure falls back to cached data and reports it as cached`() = runTest {
        local.prefill(TestData.staleTopics)
        remote.failWith(IOException("no connectivity"))

        val result = repository.getTopics()

        assertTrue(result is DataResult.Success)
        result as DataResult.Success
        assertEquals(TestData.staleTopics, result.data)
        assertEquals(Origin.CACHE, result.origin)
    }

    @Test
    fun `network failure with an empty cache reports a failure rather than empty content`() =
        runTest {
            remote.failWith(IOException("no connectivity"))

            val result = repository.getTopics()

            assertEquals(DataResult.Failure(TopicsError.NoDataAvailable), result)
        }

    @Test
    fun `cache read failure is reported as unexpected rather than crashing`() = runTest {
        remote.failWith(IOException("no connectivity"))
        local.readFailure = IllegalStateException("database corrupt")

        val result = repository.getTopics()

        assertTrue(result is DataResult.Failure)
        assertTrue((result as DataResult.Failure).error is TopicsError.Unexpected)
    }

    @Test
    fun `a failed request does not overwrite the cache`() = runTest {
        local.prefill(TestData.staleTopics)
        remote.failWith()

        repository.getTopics()

        assertTrue(local.writes.isEmpty())
    }
}
