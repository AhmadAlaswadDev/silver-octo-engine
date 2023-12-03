package com.bestcoders.zaheed.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.ui.theme.AppTheme
import com.bestcoders.zaheed.ui.theme.CustomColor
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerUI(
    modifier: Modifier = Modifier,
    showDatePicker: MutableState<Boolean>,
    onDismissRequest: (String) -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val chosenYear = remember { mutableStateOf(currentYear.toString()) }
    val chosenMonth = remember { mutableStateOf(formatMonth(currentMonth)) }
    val chosenDay = remember { mutableStateOf(formatDay(currentDay)) }

    ModalBottomSheet(
        containerColor = CustomColor.White,
        contentColor = CustomColor.White,
        sheetState = bottomSheetState,
        onDismissRequest = {
            showDatePicker.value = false
        },
        dragHandle = {
            // Picker Header
            Column(
                modifier = Modifier.background(color = CustomColor.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                content = {
                    SpacerHeightSmall()
                    Box(
                        modifier = Modifier
                            .height(5.dp)
                            .width(40.dp)
                            .background(
                                MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f),
                                RoundedCornerShape(50.dp)
                            ),
                    )
                    SpacerHeightSmall()
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextButton(
                            modifier = Modifier.weight(1f),
                            onClick = {
                                showDatePicker.value = false
                            },
                            content = {
                                Text(
                                    text = stringResource(R.string.close),
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = MaterialTheme.colorScheme.onTertiary
                                    )
                                )
                            }
                        )
                        Text(
                            modifier = Modifier.weight(3f),
                            text = stringResource(R.string.pickup_date),
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )
                        TextButton(
                            modifier = Modifier.weight(1f),
                            onClick = {
                                val formattedDate =
                                    "${chosenYear.value}-${chosenMonth.value}-${chosenDay.value}"
                                onDismissRequest(formattedDate)
                                showDatePicker.value = false
                            },
                            content = {
                                Text(
                                    text = stringResource(R.string.done),
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = MaterialTheme.colorScheme.onTertiary
                                    )
                                )
                            }
                        )
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier.background(color = CustomColor.White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                content = {
                    SpacerHeightLarge()
                    DateSelectionSection(
                        onYearChosen = { chosenYear.value = it },
                        onMonthChosen = { chosenMonth.value = formatMonth(it.toInt()) },
                        onDayChosen = { chosenDay.value = formatDay(it.toInt()) },
                    )
                    SpacerHeightLarge()
                },
            )
        },
    )
}

@Composable
fun DateSelectionSection(
    onYearChosen: (String) -> Unit,
    onMonthChosen: (String) -> Unit,
    onDayChosen: (String) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .height(AppTheme.dimens.datePickerSectionHeight)
            .background(CustomColor.White)
    ) {
        InfiniteItemsPicker(
            items = days,
            firstIndex = Int.MAX_VALUE / 2 + (currentDay - 2),
            onItemSelected = onDayChosen
        )
        InfiniteItemsPicker(
            items = monthsNames,
            firstIndex = Int.MAX_VALUE / 2 - 4 + currentMonth,
            onItemSelected = onMonthChosen
        )
        InfiniteItemsPicker(
            items = years,
            firstIndex = Int.MAX_VALUE / 2 + (currentYear - 1950),
            onItemSelected = onYearChosen
        )
    }
}

@Composable
fun InfiniteItemsPicker(
    items: List<String>,
    firstIndex: Int,
    onItemSelected: (String) -> Unit,
) {
    val listState = rememberLazyListState(firstIndex)
    val currentValue = remember { mutableStateOf("") }

    LaunchedEffect(key1 = !listState.isScrollInProgress) {
        onItemSelected(currentValue.value)
        listState.animateScrollToItem(index = listState.firstVisibleItemIndex)
    }

    Box(modifier = Modifier.height(AppTheme.dimens.datePickerHeight)) {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            state = listState,
            content = {
                items(
                    count = Int.MAX_VALUE,
                    itemContent = {
                        val index = it % items.size
                        if (it == listState.firstVisibleItemIndex + 1) {
                            currentValue.value = items[index]
                        }
                        SpacerHeightMedium()
                        Text(
                            text = items[index],
                            modifier = Modifier.alpha(if (it == listState.firstVisibleItemIndex + 1) 1f else 0.3f),
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.primary,
                            ),
                            textAlign = TextAlign.Center
                        )
                        SpacerHeightMedium()
                    },
                )
            }
        )
    }
}

private fun formatYear(year: Int): String {
    return year.toString()
}

private fun formatMonth(month: Int): String {
    return String.format(Locale.US,"%02d", month)
}

private fun formatDay(day: Int): String {
    return String.format(Locale.US,"%02d", day)
}

val currentYear = Calendar.getInstance().get(Calendar.YEAR)
val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + 1
val currentMonth = Calendar.getInstance().get(Calendar.MONTH)

val years = (1950..currentYear).map { it.toString() }
val monthsNames = listOf(
    "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
)
val days = (1..31).map { String.format(Locale.US,"%02d", it) }
