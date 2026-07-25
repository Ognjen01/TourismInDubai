package com.ognjenlazic.tourismindubai

import com.ognjenlazic.tourismindubai.data.api.dto.CategoryItemDto
import com.ognjenlazic.tourismindubai.data.api.dto.TopicsDto
import com.ognjenlazic.tourismindubai.data.api.dto.VisualItemDto
import com.ognjenlazic.tourismindubai.data.api.dto.toDomain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * The API is not under our control, so mapping has to survive a partial payload.
 */
class TopicsDtoMappingTest {

    @Test
    fun `a complete payload maps every section`() {
        val dto = TopicsDto(
            sound = listOf(CategoryItemDto(label = "Music", emoji = "🎵")),
            visuals = listOf(VisualItemDto(label = "Art", photo = "https://example.com/a.png")),
            places = listOf(CategoryItemDto(label = "Beach", emoji = "🏖️"))
        )

        val topics = dto.toDomain()

        assertEquals("Music", topics.sounds.single().label)
        assertEquals("Art", topics.visuals.single().label)
        assertEquals("Beach", topics.places.single().label)
    }

    @Test
    fun `missing sections map to empty lists instead of throwing`() {
        val topics = TopicsDto().toDomain()

        assertTrue(topics.isEmpty)
    }

    @Test
    fun `entries without a label are dropped`() {
        val dto = TopicsDto(
            sound = listOf(
                CategoryItemDto(label = null, emoji = "🎵"),
                CategoryItemDto(label = "Music", emoji = "🎵")
            )
        )

        val topics = dto.toDomain()

        assertEquals(listOf("Music"), topics.sounds.map { it.label })
    }

    @Test
    fun `a missing emoji becomes empty rather than null`() {
        val dto = TopicsDto(sound = listOf(CategoryItemDto(label = "Music", emoji = null)))

        assertEquals("", dto.toDomain().sounds.single().emoji)
    }

    @Test
    fun `visuals without a photo are dropped because there is nothing to render`() {
        val dto = TopicsDto(visuals = listOf(VisualItemDto(label = "Art", photo = null)))

        assertTrue(dto.toDomain().visuals.isEmpty())
    }
}
