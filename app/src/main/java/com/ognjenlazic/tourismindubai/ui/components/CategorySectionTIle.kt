package com.ognjenlazic.tourismindubai.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ognjenlazic.tourismindubai.ui.theme.NotificationRed
import com.ognjenlazic.tourismindubai.ui.theme.Typography

@Composable
fun CategorySectionTile(
    emoji: String,
    text: String,
    isNotificationAvailable: Boolean,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(36.dp),
        modifier = Modifier
            .wrapContentSize(),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(8.dp)
            ) {
                Box {
                    Text(
                        text = emoji,
                        style = Typography.labelSmall
                    )
                    if (isNotificationAvailable) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(NotificationRed, shape = CircleShape)
                                .align(Alignment.TopEnd)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = text,
                    style = Typography.labelSmall
                )
            }
        }
    }
}

@Preview
@Composable
fun CategorySectionTilePreview() {
    CategorySectionTile(
        emoji = "🦜",
        text = "BIRDS",
        isNotificationAvailable = true,
        onClick = {}
    )
}