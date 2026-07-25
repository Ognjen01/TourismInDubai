package com.ognjenlazic.tourismindubai.data.source

import com.ognjenlazic.tourismindubai.data.database.PlaceDao
import com.ognjenlazic.tourismindubai.data.database.PlaceEntity
import com.ognjenlazic.tourismindubai.data.database.SoundDao
import com.ognjenlazic.tourismindubai.data.database.SoundEntity
import com.ognjenlazic.tourismindubai.data.database.VisualDao
import com.ognjenlazic.tourismindubai.data.database.VisualEntity
import com.ognjenlazic.tourismindubai.data.di.IoDispatcher
import com.ognjenlazic.tourismindubai.domain.model.Topic
import com.ognjenlazic.tourismindubai.domain.model.Topics
import com.ognjenlazic.tourismindubai.domain.model.Visual
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface TopicsLocalDataSource {
    suspend fun readTopics(): Topics
    suspend fun writeTopics(topics: Topics)
}

class RoomTopicsLocalDataSource @Inject constructor(
    private val soundDao: SoundDao,
    private val visualDao: VisualDao,
    private val placeDao: PlaceDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : TopicsLocalDataSource {

    override suspend fun readTopics(): Topics = withContext(ioDispatcher) {
        Topics(
            sounds = soundDao.getAllSounds().map { Topic(it.label, it.emoji) },
            visuals = visualDao.getAllVisuals().map { Visual(it.label, it.photo) },
            places = placeDao.getAllPlaces().map { Topic(it.label, it.emoji) }
        )
    }

    override suspend fun writeTopics(topics: Topics) = withContext(ioDispatcher) {
        soundDao.insertAll(topics.sounds.map { SoundEntity(it.label, it.emoji) })
        visualDao.insertAll(topics.visuals.map { VisualEntity(it.label, it.photoUrl) })
        placeDao.insertAll(topics.places.map { PlaceEntity(it.label, it.emoji) })
    }
}
