package com.ognjenlazic.tourismindubai.data.api

import com.ognjenlazic.tourismindubai.data.api.dto.TopicsDto
import retrofit2.http.GET

interface Api {
    @GET("interview24/topics.json")
    suspend fun getTopics(): TopicsDto
}