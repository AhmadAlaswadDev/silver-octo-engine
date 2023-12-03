package com.bestcoders.zaheed.presentation.screens.product_details.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.domain.model.products.SubChoice
import com.bestcoders.zaheed.ui.theme.AppTheme
import com.bestcoders.zaheed.ui.theme.CustomColor


@Composable
fun ProductSizeComponent(
    subChoice: SubChoice,
    onSizeSelected: (Int) -> Unit,
    isSelected: Boolean,
    index: Int,
    isAvailableChoice: () -> Boolean
) {

    Box(
        modifier = Modifier
            .alpha(
                if (isAvailableChoice()) {
                    1f
                } else {
                    0.5f
                }
            )
            .fillMaxWidth()
            .height(AppTheme.dimens.filterSelectorItemHeight)
            .padding(5.dp)
            .shadow(
                elevation = 15.dp,
                shape = RoundedCornerShape(Constants.CORNER_RADUIES.dp),
                ambientColor = Color.Black.copy(alpha = 0.3f),
                spotColor = Color.Black.copy(alpha = 0.1f),
            )
            .background(
                color = if (isSelected) {
                    MaterialTheme.colorScheme.onTertiary
                } else {
                    CustomColor.White
                },
                shape = RoundedCornerShape(Constants.CORNER_RADUIES.dp),
            )
            .border(
                BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.secondaryContainer.copy(
                        alpha = 0.5f
                    )
                ),
                shape = RoundedCornerShape(Constants.CORNER_RADUIES.dp),
            )
            .clickable(
                onClick = {
                    if (!isSelected && isAvailableChoice()) {
                        onSizeSelected(index)
                    }
                },
                interactionSource = MutableInteractionSource(),
                indication = null
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .padding(10.dp),
            text = subChoice.name,
            style = MaterialTheme.typography.bodySmall.copy(
                color = if (isSelected) {
                    CustomColor.White
                } else {
                    MaterialTheme.colorScheme.primary
                },
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            ),
        )
    }
}