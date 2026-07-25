package com.ognjenlazic.tourismindubai.data.source

import com.ognjenlazic.tourismindubai.data.api.Api
import com.ognjenlazic.tourismindubai.data.api.dto.toDomain
import com.ognjenlazic.tourismindubai.domain.model.Topics
import javax.inject.Inject

interface TopicsRemoteDataSource {
    /** Loads topics from the network. Throws if the request fails. */
    suspend fun fetchTopics(): Topics
}

class RetrofitTopicsRemoteDataSource @Inject constructor(
    private val api: Api
) : TopicsRemoteDataSource {

    override suspend fun fetchTopics(): Topics = api.getTopics().toDomain()
}
