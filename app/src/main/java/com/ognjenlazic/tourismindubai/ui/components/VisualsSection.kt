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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.ognjenlazic.tourismindubai.R

@Composable
fun VisualsSection(
    title: String,
    visualData: List<VisualData>
) {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
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
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .width(160.dp)
            .height(200.dp)
            .padding(1.dp)
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