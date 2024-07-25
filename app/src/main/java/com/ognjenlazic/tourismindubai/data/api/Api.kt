package com.ognjenlazic.tourismindubai.data.api

import com.ognjenlazic.tourismindubai.data.model.TopicsResponse
import retrofit2.http.GET

interface Api {
    @GET("interview24/topics.json")
    suspend fun getTopics(): TopicsResponse
}