package com.ognjenlazic.tourismindubai.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SoundEntity::class, VisualEntity::class, PlaceEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun soundDao(): SoundDao
    abstract fun visualDao(): VisualDao
    abstract fun placeDao(): PlaceDao
}