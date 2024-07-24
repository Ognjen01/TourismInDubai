package com.ognjenlazic.tourismindubai.data.model

data class TopicsResponse(
    val Sound: List<CategoryItem>,
    val Visuals: List<VisualItem>,
    val Places: List<CategoryItem>
)

data class CategoryItem(
    val label: String,
    val emoji: String
)

data class VisualItem(
    val label: String,
    val photo: String
)
