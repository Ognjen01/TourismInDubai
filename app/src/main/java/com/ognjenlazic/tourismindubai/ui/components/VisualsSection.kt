package com.ognjenlazic.tourismindubai.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberAsyncImagePainter
import com.ognjenlazic.tourismindubai.R
import com.ognjenlazic.tourismindubai.ui.theme.Dimens
import com.ognjenlazic.tourismindubai.ui.theme.Typography

@Composable
fun VisualsSection(
    title: String,
    visualData: List<VisualData>
) {
    Column(
        modifier = Modifier.padding(horizontal = Dimens.largePadding, vertical = Dimens.smallPadding)
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(bottom = Dimens.mediumPadding),
            style = Typography.labelSmall
        )

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(bottom = Dimens.mediumPadding),
            horizontalArrangement = Arrangement.spacedBy(Dimens.mediumPadding)
        ) {
            visualData.forEach {
                VisualTile(data = it)
            }
        }
    }
}

@Composable
fun VisualTile(data: VisualData) {
    Card(
        shape = RoundedCornerShape(Dimens.smallCorner),
        modifier = Modifier
            .width(Dimens.imageWidth)
            .height(Dimens.imageHeight)
            .padding(Dimens.extraSmallPadding)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = data.photo),
            contentDescription = data.label,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}


@Preview
@Composable
fun VisualSectionPreview() {

    // Note that images can not be loaded in preview
    // We use empty Strings just for alignment check
    val data = listOf(
        VisualData("", ""),
        VisualData("", ""),
        VisualData("", "")
    )

    VisualsSection(
        title = stringResource(id = R.string.visuals),
        visualData = data
    )
}