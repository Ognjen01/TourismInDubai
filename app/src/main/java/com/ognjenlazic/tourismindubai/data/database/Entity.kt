package com.ognjenlazic.tourismindubai.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sounds")
data class SoundEntity(
    @PrimaryKey val label: String,
    val emoji: String
)

@Entity(tableName = "visuals")
data class VisualEntity(
    @PrimaryKey val label: String,
    val photo: String
)

@Entity(tableName = "places")
data class PlaceEntity(
    @PrimaryKey val label: String,
    val emoji: String
)