package ua.vitolex.buns.presentation.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ua.vitolex.buns.ui.theme.OldGreen
import ua.vitolex.buns.ui.theme.OldGrey

@Composable
fun BlockCanvas() {
    val configuration = LocalConfiguration.current
    val width = configuration.screenWidthDp.dp
    val density = configuration.densityDpi

    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary

    fun convertToPx(dp: Int): Float {
        val pixels = dp * (density / 160f)
        return pixels.toFloat()
    }

    Canvas(
        modifier = Modifier
            .width(configuration.screenWidthDp.dp).background(primaryColor)
            .height(200.dp)
            .padding(0.dp)
    ) {
        drawRoundRect(
            color = OldGrey,
            cornerRadius = CornerRadius(convertToPx(10), convertToPx(10)),
            topLeft = Offset(0f, size.height - convertToPx(165)),
            size = Size(size.width - convertToPx(35), size.height - convertToPx(35)),
        )
        drawRoundRect(
            color = OldGrey,
            cornerRadius = CornerRadius(convertToPx(10), convertToPx(10)),
            topLeft = Offset(0f, size.height - convertToPx(147)),
            size = Size(size.width, size.height - convertToPx(45)),
        )
        drawRoundRect(
            color = OldGrey,
            cornerRadius = CornerRadius(convertToPx(10), convertToPx(10)),
            topLeft = Offset(0f, 0f),
            size = Size(size.width - convertToPx(75), size.height),
        )
        drawRoundRect(
            color = OldGrey,
            cornerRadius = CornerRadius(convertToPx(10), convertToPx(10)),
            topLeft = Offset(0f, 0f),
            size = Size(size.width - convertToPx(54), size.height),
        )
        drawRoundRect(
            color = primaryColor,
            cornerRadius = CornerRadius(45f, 45f),
            topLeft = Offset(size.width - convertToPx(54), convertToPx(-1)),
            size = Size(convertToPx(62), convertToPx(54)),
        )
    }
}

@Composable
@Preview
fun BlockCanvasPrew() {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.Yellow)
            .padding(10.dp)
    ) {
        BlockCanvas()
    }

}
