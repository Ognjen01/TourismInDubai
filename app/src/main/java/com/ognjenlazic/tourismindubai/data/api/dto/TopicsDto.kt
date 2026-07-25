package com.ognjenlazic.tourismindubai.data.api.dto

import com.google.gson.annotations.SerializedName
import com.ognjenlazic.tourismindubai.domain.model.Topic
import com.ognjenlazic.tourismindubai.domain.model.Topics
import com.ognjenlazic.tourismindubai.domain.model.Visual

/**
 * Transport models.
 *
 * Every field is nullable on purpose. Gson will happily construct a Kotlin data class with
 * nulls in non-null fields if the payload omits them, which turns a malformed response into
 * a NullPointerException somewhere far away in the UI. Declaring the nullability here and
 * dropping incomplete entries during mapping keeps that failure local and recoverable.
 */
data class TopicsDto(
    @SerializedName("Sound") val sound: List<CategoryItemDto>? = null,
    @SerializedName("Visuals") val visuals: List<VisualItemDto>? = null,
    @SerializedName("Places") val places: List<CategoryItemDto>? = null
)

data class CategoryItemDto(
    @SerializedName("label") val label: String? = null,
    @SerializedName("emoji") val emoji: String? = null
)

data class VisualItemDto(
    @SerializedName("label") val label: String? = null,
    @SerializedName("photo") val photo: String? = null
)

fun TopicsDto.toDomain(): Topics = Topics(
    sounds = sound.orEmpty().mapNotNull { it.toDomain() },
    visuals = visuals.orEmpty().mapNotNull { it.toDomain() },
    places = places.orEmpty().mapNotNull { it.toDomain() }
)

private fun CategoryItemDto.toDomain(): Topic? {
    val label = label ?: return null
    return Topic(label = label, emoji = emoji.orEmpty())
}

private fun VisualItemDto.toDomain(): Visual? {
    val label = label ?: return null
    val photo = photo ?: return null
    return Visual(label = label, photoUrl = photo)
}
