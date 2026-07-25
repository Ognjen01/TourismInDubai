package com.ognjenlazic.tourismindubai.domain.model

/**
 * The topic catalogue as the rest of the app understands it.
 *
 * This is the domain shape, deliberately kept separate from the transport shape in
 * [com.ognjenlazic.tourismindubai.data.api.dto]. The API uses capitalised field names and
 * nullable strings; nothing above the data layer should have to know that.
 */
data class Topics(
    val sounds: List<Topic>,
    val visuals: List<Visual>,
    val places: List<Topic>
) {
    val isEmpty: Boolean
        get() = sounds.isEmpty() && visuals.isEmpty() && places.isEmpty()

    companion object {
        val EMPTY = Topics(emptyList(), emptyList(), emptyList())
    }
}

data class Topic(
    val label: String,
    val emoji: String
)

data class Visual(
    val label: String,
    val photoUrl: String
)
