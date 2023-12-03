package com.bestcoders.zaheed.presentation.screens.store_details.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.sp
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.extentions.removePadding
import com.bestcoders.zaheed.presentation.components.SpacerWidthLarge
import com.bestcoders.zaheed.presentation.components.SpacerWidthMedium
import com.bestcoders.zaheed.ui.theme.AppTheme
import com.bestcoders.zaheed.ui.theme.CustomColor

@Composable
fun CategorySelector(
    selectedItem: MutableState<Int>,
    list: List<String>?,
    onClick: (Int) -> Unit,
    productCount: Int
) {
    Column(
        modifier = Modifier.fillMaxWidth().removePadding(-AppTheme.dimens.paddingHorizontal),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            content = {
                list?.forEachIndexed { index, item ->
                    val isSelected = selectedItem.value == index
                    if (item != list.first()) {
                        SpacerWidthMedium()
                    } else {
                        SpacerWidthLarge()
                    }
                    if (isSelected) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterVertically)
                                .background(color = CustomColor.White)
                                .clickable(
                                    role = Role.Switch,
                                    enabled = true,
                                    onClick = {
                                        selectedItem.value = index
                                        onClick(index)
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
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    textDecoration = TextDecoration.Underline,
                                    letterSpacing = 1.sp,
                                    lineHeight = 50.sp
                                ),
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterVertically)
                                .background(color = CustomColor.White)
                                .clickable(
                                    role = Role.Switch,
                                    enabled = true,
                                    onClick = {
                                        selectedItem.value = index
                                        onClick(index)
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
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    letterSpacing = 1.sp
                                )
                            )
                        }
                    }
                    if (item == list.last()) {
                        SpacerWidthLarge()
                    }
                }
            }
        )
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            content = {
                SpacerWidthLarge()
                Text(
                    modifier = Modifier,
                    text = productCount.toString(),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.secondary,
                    )
                )
                Text(
                    modifier = Modifier,
                    text = " " + stringResource(R.string.results),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.secondary.copy(
                            alpha = 0.4f
                        ),
                    )
                )
            }
        )
    }
}
