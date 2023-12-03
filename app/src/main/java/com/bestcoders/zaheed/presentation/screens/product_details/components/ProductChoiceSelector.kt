package com.bestcoders.zaheed.presentation.screens.product_details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bestcoders.zaheed.core.util.header
import com.bestcoders.zaheed.domain.model.products.ColorModel
import com.bestcoders.zaheed.domain.model.products.SubChoice
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun ProductChoiceSelector(
    subChoices: List<SubChoice>,
    name: String,
    index1: Int,
    selectedVariant: MutableState<String>,
    availableVariations: MutableList<String>,
    colors: MutableList<ColorModel>,
    onChoiceSelected: (String) -> Unit,
) {
    val finalNewVariant = remember {
        mutableStateOf("")
    }
    val itemsPerRow = 3
    val numRows = (subChoices.size + itemsPerRow - 1) / itemsPerRow
    val gridHeight = numRows * AppTheme.dimens.filterSelectorItemHeight.value + 50
    val selectedSizeIndex = remember { mutableStateOf(0) }

    fun getProductSelectedChoice(index: Int): String {
        val array = selectedVariant.value.split("-").toTypedArray()
        var finalIndex = if (colors.isEmpty()) index else index + 1
        finalIndex = if (array.size == 1) 0 else finalIndex
        return array.getOrElse(finalIndex) { "" }
    }

    fun changeProductSelectedChoice(choice: String, index: Int): String {
        val array = selectedVariant.value.split("-").toMutableList()
        var finalIndex = if (colors.isEmpty()) index else index + 1
        finalIndex = if (array.size == 1) 0 else finalIndex
        array[finalIndex] = choice
        finalNewVariant.value = array.joinToString("-")
        return array.joinToString("-")
    }


    fun isAvailableChoice(choice: String, index: Int): Boolean {
        val finalVariant = changeProductSelectedChoice(choice, index)
        return availableVariations.contains(finalVariant)
    }

    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxWidth()
            .height(gridHeight.dp)
            .padding(horizontal = AppTheme.dimens.paddingHorizontal),
        columns = GridCells.Fixed(itemsPerRow),
        horizontalArrangement = Arrangement.SpaceBetween,
        content = {
            header {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start
                    ),
                )
            }
            items(
                count = subChoices.size,
                key = {
                    subChoices[it].id
                },
                itemContent = { index ->
                    ProductSizeComponent(
                        index = index,
                        isSelected = getProductSelectedChoice(index1) == subChoices[index].value,
                        subChoice = subChoices[index],
                        isAvailableChoice = {
                            isAvailableChoice(
                                subChoices[index].value,
                                index1
                            )
                        },
                        onSizeSelected = { newIndex ->
                            selectedSizeIndex.value = newIndex
                            onChoiceSelected(finalNewVariant.value)
                        }
                    )
                }
            )
        }
    )
}
