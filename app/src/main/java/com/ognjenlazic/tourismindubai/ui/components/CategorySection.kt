package com.ognjenlazic.tourismindubai.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.ognjenlazic.tourismindubai.R
import com.ognjenlazic.tourismindubai.ui.theme.Dimens
import com.ognjenlazic.tourismindubai.ui.theme.Typography

@Composable
fun CategorySection(
    title: String,
    sectionData: List<TileData>
) {
    val halfSize = (sectionData.size + 1) / 2
    val firstHalf = sectionData.take(halfSize)
    val secondHalf = sectionData.drop(halfSize)

    Column(
        modifier = Modifier.padding(horizontal = Dimens.largePadding, vertical = Dimens.smallPadding)
    ) {
        Text(
            text = title,
            style = Typography.labelLarge,
            modifier = Modifier.padding(bottom = Dimens.mediumPadding)
        )

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(bottom = Dimens.mediumPadding)
        ) {
            Column {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimens.mediumPadding),
                    modifier = Modifier.padding(bottom = Dimens.mediumPadding)
                ) {
                    firstHalf.forEach { item ->
                        CategorySectionTile(
                            emoji = item.emoji,
                            text = item.text,
                            isNotificationAvailable = item.isNotificationAvailable,
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(Dimens.mediumPadding)
                ) {
                    secondHalf.forEach { item ->
                        CategorySectionTile(
                            emoji = item.emoji,
                            text = item.text,
                            isNotificationAvailable = item.isNotificationAvailable,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CategorySectionPreview(){

    val soundOptions = listOf(
        TileData("🦜", "BIRDS", false),
        TileData("🌊", "OCEAN", true),
        TileData("🎧", "LIVE", true),
        TileData("🔥", "FIRE", false),
        TileData("🤫", "SILENCE", false)
    )

    CategorySection(
        title = stringResource(id = R.string.sound),
        sectionData = soundOptions
    )
}
