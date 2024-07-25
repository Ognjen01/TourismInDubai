package com.ognjenlazic.tourismindubai.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ognjenlazic.tourismindubai.ui.theme.NotificationRed
import com.ognjenlazic.tourismindubai.ui.theme.TileGray
import com.ognjenlazic.tourismindubai.ui.theme.Typography

@Composable
fun CategorySectionTile(
    emoji: String,
    text: String,
    isNotificationAvailable: Boolean,
) {
    var isFlipped by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(if (isFlipped) 180f else 0f, label = "")
    val interactionSource = remember { MutableInteractionSource() }

    Card(
        shape = RoundedCornerShape(36.dp),
        colors = CardDefaults.cardColors(
            containerColor = TileGray,
        ),
        modifier = Modifier
            .wrapContentSize()
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            }
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                isFlipped = !isFlipped
            }
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp)
        ) {
            if (rotation < 90f || rotation > 270f) {
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
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "✅",
                        style = Typography.labelSmall
                    )
                }
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
    )
}