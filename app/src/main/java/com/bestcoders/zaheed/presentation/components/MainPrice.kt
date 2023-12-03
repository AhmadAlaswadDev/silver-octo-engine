package com.bestcoders.zaheed.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.bestcoders.zaheed.core.extentions.removeZerosAfterComma
import com.bestcoders.zaheed.core.extentions.roundTo1DecimalPlace
import com.bestcoders.zaheed.core.util.Constants

@Composable
fun MainPrice(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodySmall.copy(
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Start,
        color = MaterialTheme.colorScheme.onPrimary,
    ),
    price: String
) {
    Row(
        modifier = modifier.wrapContentSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
    ) {
        Text(
            text = price.replace(",", ".").toDouble()
                .roundTo1DecimalPlace().toString().removeZerosAfterComma().replace(",", ".")
                    + " " + Constants.MAIN_CURENCY,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            style = textStyle,
        )
    }
}

