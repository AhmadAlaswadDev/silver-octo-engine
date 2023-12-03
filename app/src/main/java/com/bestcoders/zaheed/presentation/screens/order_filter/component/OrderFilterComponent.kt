package com.bestcoders.zaheed.presentation.screens.order_filter.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bestcoders.zaheed.ui.theme.AppTheme
import com.bestcoders.zaheed.ui.theme.CustomColor

@Composable
fun OrderFilterComponent(
    index: Int,
    listValues: List<String>,
    listNames: List<String>,
    selectedItem: MutableState<Int>,
    onClick: (String) -> Unit,
) {
    val isSelected = selectedItem.value == index
    if (isSelected) {
        Box(
            modifier = Modifier
                .width(AppTheme.dimens.filterSelectorItemWidth)
                .height(AppTheme.dimens.filterSelectorItemHeight)
                .padding(5.dp)
                .shadow(
                    elevation = 15.dp,
                    shape = RoundedCornerShape(100.dp),
                    ambientColor = Color.Black.copy(alpha = 0.3f),
                    spotColor = Color.Black.copy(alpha = 0.1f),
                )
                .background(
                    color = CustomColor.White,
                    shape = RoundedCornerShape(100.dp),
                )
                .border(
                    BorderStroke(1.dp, MaterialTheme.colorScheme.onPrimary),
                    shape = RoundedCornerShape(100.dp),
                )
                .clickable(
                    role = Role.Switch,
                    enabled = true,
                    onClick = {
                        selectedItem.value = index
                        onClick(listValues[index])
                    },
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .padding(10.dp),
                text = listNames[index].replace("_", " "),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                ),
            )
        }
    } else {
        Box(
            modifier = Modifier
                .width(AppTheme.dimens.filterSelectorItemWidth)
                .height(AppTheme.dimens.filterSelectorItemHeight)
                .padding(5.dp)
                .shadow(
                    elevation = 15.dp,
                    shape = RoundedCornerShape(100.dp),
                    ambientColor = Color.Black.copy(alpha = 0.3f),
                    spotColor = Color.Black.copy(alpha = 0.1f),
                )
                .background(
                    color = CustomColor.White,
                    shape = RoundedCornerShape(100.dp),
                )
                .border(
                    BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.secondaryContainer.copy(
                            alpha = 0.5f
                        )
                    ),
                    shape = RoundedCornerShape(100.dp),
                )
                .clickable(
                    role = Role.Switch,
                    enabled = true,
                    onClick = {
                        selectedItem.value = index
                        onClick(listValues[index])
                    },
                    interactionSource = MutableInteractionSource(),
                    indication = null
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .padding(10.dp),
                text = listNames[index].replace("_", " "),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                ),
            )
        }
    }
}