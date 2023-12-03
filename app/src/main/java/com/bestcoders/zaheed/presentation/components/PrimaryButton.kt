package com.bestcoders.zaheed.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.ui.theme.AppTheme
import com.bestcoders.zaheed.ui.theme.CustomColor

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    onClick: () -> Unit,
    color: Color = MaterialTheme.colorScheme.primaryContainer,
    disabledColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    radius: Float = Constants.CORNER_RADUIES.toFloat(),
    borderColor: Color = MaterialTheme.colorScheme.primaryContainer,
    borderStroke: Float = 0f,
    progressColor: Color = CustomColor.White,
    progressSize: Float = 25f,
    textStyle: TextStyle = MaterialTheme.typography.headlineSmall.copy(
        color = CustomColor.White,
        fontWeight = FontWeight.Bold,
    ),
    isFormValidate: Boolean = true,
    icon: Int? = null,
    iconColor: Color = CustomColor.White,
    iconSize: Dp = 30.dp
) {
    return Button(
        enabled = enabled,
        onClick = onClick,
        contentPadding = PaddingValues(0.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(AppTheme.dimens.buttonHeight),
        colors = if (!isFormValidate && !isLoading) {
            ButtonDefaults.buttonColors(
                containerColor = disabledColor,
                disabledContentColor = disabledColor,
                disabledContainerColor = disabledColor,
            )
        } else {
            ButtonDefaults.buttonColors(
                containerColor = color,
                disabledContentColor = color,
                disabledContainerColor = color,
            )
        },
        border = if (isFormValidate) {
            BorderStroke(borderStroke.dp, borderColor)
        } else {
            BorderStroke(borderStroke.dp, disabledColor)
        },
        shape = RoundedCornerShape(radius.dp)
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(progressSize.dp), color = progressColor
            )
        } else {
            Text(
                text,
                style = textStyle
            )
            SpacerWidthMedium()
            if (icon != null) {
                Icon(
                    modifier = Modifier.size(iconSize),
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    tint = iconColor
                )
            }
        }
    }
}