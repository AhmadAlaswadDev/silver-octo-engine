package com.bestcoders.zaheed.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun OtpTextField(
    modifier: Modifier = Modifier,
    otpText: String,
    otpCount: Int = 6,
    onOtpTextChange: (String, Boolean) -> Unit,
    errorMessage: String? = null,
    showError: Boolean = false,
) {
    LaunchedEffect(Unit) {
        if (otpText.length > otpCount) {
            throw IllegalArgumentException("Otp text value must not have more than otpCount: $otpCount characters")
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        if (showError && !errorMessage.isNullOrEmpty()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = errorMessage,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color.Red
                ),
                textAlign = TextAlign.Center
            )
        }
        SpacerHeightMedium()
        BasicTextField(
            modifier = Modifier.fillMaxWidth(),
            value = TextFieldValue(otpText, selection = TextRange(otpText.length)),
            onValueChange = {
                if (it.text.length <= otpCount) {
                    onOtpTextChange.invoke(it.text, it.text.length == otpCount)
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            decorationBox = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(otpCount) { index ->
                        CharView(
                            modifier = Modifier
                                .weight(1f)
                                .align(Alignment.Top),
                            index = index, text = otpText
                        )
                        SpacerWidthSmall()
                        SpacerWidthSmall()
                    }
                }

            },
        )
    }
}

@Composable
private fun CharView(
    modifier: Modifier,
    index: Int, text: String,
    errorMessage: String? = null,
    showError: Boolean = false,
) {
    val char = when {
        index == text.length -> ""
        index > text.length -> ""
        else -> text[index].toString()
    }
    Text(
        modifier = modifier
            .width(AppTheme.dimens.otpFieldSize)
            .height(AppTheme.dimens.otpFieldSize)
            .border(
                1.dp, when {
                    showError && errorMessage.isNullOrEmpty() -> MaterialTheme.colorScheme.errorContainer
                    char.isNotEmpty() -> MaterialTheme.colorScheme.onSecondary
                    else -> MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)
                }, RoundedCornerShape(8.dp)
            )
            .padding(5.dp),
        text = char,
        style = MaterialTheme.typography.headlineSmall.copy(
            fontWeight = FontWeight.Bold
        ),
        color = if (char.isEmpty()) {
            MaterialTheme.colorScheme.secondary
        } else {
            MaterialTheme.colorScheme.secondary
        },
        textAlign = TextAlign.Center
    )
}