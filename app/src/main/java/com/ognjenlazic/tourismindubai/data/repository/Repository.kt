package com.ognjenlazic.tourismindubai.data.repository

import com.ognjenlazic.tourismindubai.data.api.Api
import com.ognjenlazic.tourismindubai.data.database.PlaceDao
import com.ognjenlazic.tourismindubai.data.database.PlaceEntity
import com.ognjenlazic.tourismindubai.data.database.SoundDao
import com.ognjenlazic.tourismindubai.data.database.SoundEntity
import com.ognjenlazic.tourismindubai.data.database.VisualDao
import com.ognjenlazic.tourismindubai.data.database.VisualEntity
import com.ognjenlazic.tourismindubai.data.model.CategoryItem
import com.ognjenlazic.tourismindubai.data.model.TopicsResponse
import com.ognjenlazic.tourismindubai.data.model.VisualItem
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Singleton
class Repository @Inject constructor(
    private val api: Api,
    private val soundDao: SoundDao,
    private val visualDao: VisualDao,
    private val placeDao: PlaceDao
) {
    suspend fun getTopics(): TopicsResponse {
        return try {
            val response = api.getTopics()
            cacheTopics(response)
            response
        } catch (e: Exception) {
            loadCachedTopics()
        }
    }

    private suspend fun cacheTopics(response: TopicsResponse) {
        withContext(Dispatchers.IO) {
            soundDao.insertAll(response.Sound.map { SoundEntity(it.label, it.emoji) })
            visualDao.insertAll(response.Visuals.map { VisualEntity(it.label, it.photo) })
            placeDao.insertAll(response.Places.map { PlaceEntity(it.label, it.emoji) })
        }
    }

    private suspend fun loadCachedTopics(): TopicsResponse {
        return withContext(Dispatchers.IO) {
            val sounds = soundDao.getAllSounds().map { CategoryItem(it.label, it.emoji) }
            val visuals = visualDao.getAllVisuals().map { VisualItem(it.label, it.photo) }
            val places = placeDao.getAllPlaces().map { CategoryItem(it.label, it.emoji) }
            TopicsResponse(sounds, visuals, places)
        }
    }
}