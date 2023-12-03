package com.bestcoders.zaheed.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bestcoders.zaheed.core.extentions.removeZerosAfterComma
import com.bestcoders.zaheed.core.extentions.roundTo1DecimalPlace
import com.bestcoders.zaheed.core.util.Constants

@Composable
fun StrokedPrice(price: String) {
    Row(
        modifier = Modifier.wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
    ) {
        var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

        Text(
            modifier = Modifier.drawWithContent {
                drawContent()
                textLayoutResult?.apply {
                    val strokeWidth = 1.5.dp.toPx()
                    for (i in 0 until lineCount) {
                        val verticalCenter =
                            (getLineTop(i) + ((getLineBottom(i) - getLineTop(i)) / 2)) + (2 * strokeWidth)
                        drawLine(
                            color = Color.Red,
                            strokeWidth = strokeWidth,
                            start = Offset(-5f, verticalCenter),
                            end = Offset(getLineRight(i) + 5, verticalCenter)
                        )
                    }
                }

            },
            onTextLayout = { textLayoutResult = it },
            text =  price.replace(",", ".").toDouble()
                .roundTo1DecimalPlace().toString().removeZerosAfterComma().replace(",", ".")
                    + " " + Constants.MAIN_CURENCY,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Start,
                color = MaterialTheme.colorScheme.secondary,
            ),
        )
    }
}