package com.ognjenlazic.tourismindubai.fake

import com.ognjenlazic.tourismindubai.data.source.TopicsLocalDataSource
import com.ognjenlazic.tourismindubai.data.source.TopicsRemoteDataSource
import com.ognjenlazic.tourismindubai.domain.DataResult
import com.ognjenlazic.tourismindubai.domain.model.Topic
import com.ognjenlazic.tourismindubai.domain.model.Topics
import com.ognjenlazic.tourismindubai.domain.model.Visual
import com.ognjenlazic.tourismindubai.domain.repository.TopicsRepository
import java.io.IOException

object TestData {
    val topics = Topics(
        sounds = listOf(Topic(label = "Music", emoji = "🎵")),
        visuals = listOf(Visual(label = "Art", photoUrl = "https://example.com/art.png")),
        places = listOf(Topic(label = "Beach", emoji = "🏖️"))
    )

    val staleTopics = Topics(
        sounds = listOf(Topic(label = "Cached music", emoji = "🎵")),
        visuals = emptyList(),
        places = emptyList()
    )
}

class FakeRemoteDataSource(
    var result: Result<Topics> = Result.success(TestData.topics)
) : TopicsRemoteDataSource {

    var fetchCount = 0
        private set

    override suspend fun fetchTopics(): Topics {
        fetchCount++
        return result.getOrThrow()
    }

    fun failWith(error: Throwable = IOException("network down")) {
        result = Result.failure(error)
    }

    fun succeedWith(topics: Topics = TestData.topics) {
        result = Result.success(topics)
    }
}

class FakeLocalDataSource(
    private var stored: Topics = Topics.EMPTY
) : TopicsLocalDataSource {

    var readFailure: Throwable? = null
    val writes = mutableListOf<Topics>()

    override suspend fun readTopics(): Topics {
        readFailure?.let { throw it }
        return stored
    }

    override suspend fun writeTopics(topics: Topics) {
        writes += topics
        stored = topics
    }

    fun prefill(topics: Topics) {
        stored = topics
    }
}

class FakeTopicsRepository(
    var result: DataResult<Topics> = DataResult.Success(
        TestData.topics,
        com.ognjenlazic.tourismindubai.domain.Origin.REMOTE
    )
) : TopicsRepository {

    var callCount = 0
        private set

    override suspend fun getTopics(): DataResult<Topics> {
        callCount++
        return result
    }
}
