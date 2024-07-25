package com.ognjenlazic.tourismindubai.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ognjenlazic.tourismindubai.ui.theme.CapsuleLightBlue
import com.ognjenlazic.tourismindubai.ui.theme.Dimens
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
        shape = RoundedCornerShape(Dimens.mediumCorner),
        colors = CardDefaults.cardColors(
            containerColor = if (isFlipped) CapsuleLightBlue else TileGray,
        ),
        modifier = Modifier
            .width(180.dp)
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
            modifier = Modifier
                .padding(
                    horizontal = Dimens.largePadding,
                    vertical = Dimens.mediumPadding
                )
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (rotation < 90f || rotation > 270f) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(Dimens.mediumPadding)
                ) {
                    Box {
                        Text(
                            text = emoji,
                            style = Typography.labelSmall
                        )
                        if (isNotificationAvailable) {
                            Box(
                                modifier = Modifier
                                    .size(Dimens.mediumMargin)
                                    .background(NotificationRed, shape = CircleShape)
                                    .align(Alignment.TopEnd)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(Dimens.mediumMargin))
                    Text(
                        text = text,
                        style = Typography.labelSmall
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(Dimens.mediumPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White
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