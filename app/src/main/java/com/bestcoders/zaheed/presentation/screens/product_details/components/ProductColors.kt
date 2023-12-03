package com.bestcoders.zaheed.presentation.screens.product_details.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.extentions.color
import com.bestcoders.zaheed.domain.model.products.ColorModel
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun ProductColors(
    colors: MutableList<ColorModel>,
    selectedItem: MutableState<Int>,
    onClick: (String) -> Unit,
    selectedVariant: MutableState<String>,
    availableVariations: MutableList<String>,
) {
    val finalNewVariant = remember {
        mutableStateOf("")
    }
    fun changeProductSelectedColor(color: String): String {
        val array = selectedVariant.value.split("-").toMutableList()
        array[0] = color
        finalNewVariant.value = array.joinToString("-")
        return array.joinToString("-")
    }

    fun getProductSelectedColor(): String {
        val array = selectedVariant.value.split("-").toTypedArray()
        return array.firstOrNull() ?: ""
    }

    fun isAvailableColor(color: String): Boolean {
        val finalVariant = changeProductSelectedColor(color)
        return availableVariations.contains(finalVariant)
    }

    val itemsPerRow = 5
    val numRows = (colors.size + itemsPerRow - 1) / itemsPerRow
    val gridHeight = numRows * AppTheme.dimens.productBoxColorSize.value + 50

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.dimens.paddingHorizontal),
        text = stringResource(R.string.choose_color),
        overflow = TextOverflow.Clip,
        maxLines = 1,
        style = MaterialTheme.typography.bodyLarge.copy(
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.primary
        ),
    )
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .height(gridHeight.dp)
            .padding(horizontal = AppTheme.dimens.paddingHorizontal),
        columns = GridCells.Fixed(6),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalArrangement = Arrangement.spacedBy(15.dp),
    ) {
        items(
            count = colors.size,
            key = {
                colors[it].hashCode()
            }
        ) { index ->
            val isSelected = getProductSelectedColor() == colors[index].originalName
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .alpha(
                            if (isAvailableColor(colors[index].originalName)) {
                                1f
                            } else {
                                0.3f
                            }
                        )
                        .size(AppTheme.dimens.productBoxColorSize)
                        .border(
                            border = BorderStroke(
                                width = 3.dp,
                                color = MaterialTheme.colorScheme.onTertiary
                            ),
                            shape = CircleShape
                        )
                        .background(color = Color.Transparent, shape = CircleShape),
                    contentAlignment = Alignment.Center,
                    content = {
                        Box(
                            modifier = Modifier
                                .clickable(
                                    role = Role.Switch,
                                    enabled = true,
                                    onClick = {
                                        if (isAvailableColor(colors[index].originalName)) {
                                            selectedItem.value = index
                                            onClick(finalNewVariant.value)
                                        }

                                    },
                                    interactionSource = MutableInteractionSource(),
                                    indication = null
                                )
                                .size(AppTheme.dimens.productColorSize)
                                .background(color = colors[index].code.color, shape = CircleShape)
                        )
                    },
                )
            } else {
                Box(
                    modifier = Modifier
                        .alpha(
                            if (isAvailableColor(colors[index].originalName)) {
                                1f
                            } else {
                                0.3f
                            }
                        )
                        .size(AppTheme.dimens.productBoxColorSize)
                        .background(
                            color = Color.Transparent, shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center,
                    content = {
                        Box(
                            modifier = Modifier
                                .clickable(
                                    role = Role.Switch,
                                    enabled = true,
                                    onClick = {
                                        if (isAvailableColor(colors[index].originalName)) {
                                            selectedItem.value = index
                                            onClick(finalNewVariant.value)
                                        }
                                    },
                                    interactionSource = MutableInteractionSource(),
                                    indication = null
                                )
                                .size(AppTheme.dimens.productColorSize)
                                .background(color = colors[index].code.color, shape = CircleShape)
                        )
                    },
                )
            }
        }
    }
}