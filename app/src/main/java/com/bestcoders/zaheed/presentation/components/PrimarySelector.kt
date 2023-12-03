package com.bestcoders.zaheed.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.ui.theme.AppTheme
import com.bestcoders.zaheed.ui.theme.CustomColor

@Composable
fun PrimarySelector(
    modifier: Modifier = Modifier,
    selectedItem: MutableState<Int>,
    items: List<String>,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(AppTheme.dimens.genderSelectorHeight)
            .background(
                color = MaterialTheme.colorScheme.onTertiary.copy(alpha = 0.1f),
                shape = RoundedCornerShape(Constants.CORNER_RADUIES.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = selectedItem.value == index
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(AppTheme.dimens.small)
                    .align(Alignment.CenterVertically)
                    .background(
                        color = if (isSelected) CustomColor.White else Color.Transparent,
                        shape = RoundedCornerShape(Constants.CORNER_RADUIES.dp)
                    )
                    .clickable(
                        role = Role.Switch,
                        enabled = true,
                        onClick = {
                            selectedItem.value = index
                        },
                        interactionSource = MutableInteractionSource(),
                        indication = null
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.Center),
                    text = item,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}
