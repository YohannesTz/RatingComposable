package com.github.yohannestz.ratingcomposable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.math.min


@Composable
fun RatingComponent(sliderValue: Float) {
    val dynamicColor = lerp(
        startColor = Color(0xFFfc4e03),
        endColor = Color(0xFFffb803),
        fraction = sliderValue
    )

    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxWidth()) {
        Canvas(modifier = Modifier.size(300.dp, 300.dp)) {
            drawCircle(
                color = dynamicColor,
                center = Offset(size.width / 2, size.height / 2),
                radius = size.minDimension / 3 - 1.dp.toPx(),
            )

            //draw eyes and blush
            if (sliderValue < 0.5) {
                drawCircle(
                    color = Color.Black,
                    center = Offset(size.width / 2 - 30.dp.toPx(), size.height / 2 - 30.dp.toPx()),
                    radius = 8.dp.toPx(),
                )
                drawCircle(
                    color = Color.Black,
                    center = Offset(size.width / 2 + 30.dp.toPx(), size.height / 2 - 30.dp.toPx()),
                    radius = 8.dp.toPx(),
                )

            } else {
                drawVShape(
                    color = Color.Black,
                    strokeWidth = 10f,
                    centerX = size.width / 2 - 30.dp.toPx(),
                    centerY = size.height / 2  - 30.dp.toPx(),
                    size = min(size.width, size.height) * 0.5f
                )

                drawVShape(
                    color = Color.Black,
                    strokeWidth = 10f,
                    centerX = size.width / 2 + 30.dp.toPx(),
                    centerY = size.height / 2  - 30.dp.toPx(),
                    size = min(size.width, size.height) * 0.5f
                )

                drawCircle(
                    brush = Brush.radialGradient(
                        listOf(Color(0xFFfc4e03), dynamicColor),
                        Offset(size.width / 2 - 50.dp.toPx(), size.height / 2 + 10.dp.toPx()),
                        radius = 20.dp.toPx()
                    ),
                    center = Offset(size.width / 2 - 50.dp.toPx(), size.height / 2 + 10.dp.toPx()),
                    radius = 20.dp.toPx(),
                )

                drawCircle(
                    brush = Brush.radialGradient(
                        listOf(Color(0xFFfc4e03), dynamicColor),
                        Offset(size.width / 2 + 50.dp.toPx(), size.height / 2 + 10.dp.toPx()),
                        radius = 20.dp.toPx()
                    ),
                    center = Offset(size.width / 2 + 50.dp.toPx(), size.height / 2 + 10.dp.toPx()),
                    radius = 20.dp.toPx(),
                )
            }

            //draw mouth
            drawArc(
                color = Color.Black,
                startAngle = 0f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = Offset(size.width / 2 - 30.dp.toPx(), size.height / 2 - 5.dp.toPx()),
                size = Size(60.dp.toPx(), (sliderValue * 50.dp.toPx())),
                style = Stroke(width = 4.dp.toPx())
            )
        }
    }
}

//drawing eyes
fun DrawScope.drawVShape(
    color: Color,
    strokeWidth: Float,
    centerX: Float,
    centerY: Float,
    size: Float
) {
    val halfSize = size / 2
    val lineLength = halfSize / 4

    // Draw first line
    drawLine(
        color = color,
        start = Offset(centerX - lineLength + 30, centerY + lineLength),
        end = Offset(centerX, centerY),
        strokeWidth = strokeWidth
    )

    // Draw second line
    drawLine(
        color = color,
        start = Offset(centerX, centerY),
        end = Offset(centerX + lineLength - 30, centerY + lineLength),
        strokeWidth = strokeWidth
    )
}

fun lerp(startColor: Color, endColor: Color, fraction: Float): Color {
    return Color(
        lerp(startColor.red, endColor.red, fraction),
        lerp(startColor.green, endColor.green, fraction),
        lerp(startColor.blue, endColor.blue, fraction),
        lerp(startColor.alpha, endColor.alpha, fraction)
    )
}

fun lerp(startValue: Float, endValue: Float, fraction: Float): Float {
    return startValue + fraction * (endValue - startValue)
}