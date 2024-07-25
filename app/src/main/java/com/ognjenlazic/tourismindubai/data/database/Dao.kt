package com.ognjenlazic.tourismindubai.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ognjenlazic.tourismindubai.data.model.CategoryItem

@Dao
interface SoundDao {
    @Query("SELECT * FROM sounds")
    suspend fun getAllSounds(): List<CategoryItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(sounds: List<SoundEntity>)
}

@Dao
interface VisualDao {
    @Query("SELECT * FROM visuals")
    suspend fun getAllVisuals(): List<VisualEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(visuals: List<VisualEntity>)
}

@Dao
interface PlaceDao {
    @Query("SELECT * FROM places")
    suspend fun getAllPlaces(): List<PlaceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(places: List<PlaceEntity>)
}