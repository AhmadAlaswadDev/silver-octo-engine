package com.bestcoders.zaheed.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.ui.theme.AppTheme
import com.bestcoders.zaheed.ui.theme.CustomColor
import java.util.Calendar
import java.util.Locale

// Update the years, monthsNames, and days lists with hours and minutes lists
val hours = (1..23).map { String.format(Locale.US, "%02d", it) }
val minutes = (1..59).map { String.format(Locale.US, "%02d", it) }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimePickerUI(
    onDismissRequest: (chosenDate: String, chosenTime: String) -> Unit,
    showDateTimePicker: MutableState<Boolean>
) {
    var isDatePickerVisible by remember { mutableStateOf(true) }
    val chosenHour = remember { mutableStateOf("00") }
    val chosenMinute = remember { mutableStateOf("00") }
    val chosenYear = remember { mutableStateOf(DateTimePickerCustom.currentYear.toString()) }
    val chosenMonth =
        remember { mutableStateOf(DateTimePickerCustom.formatMonth(DateTimePickerCustom.currentMonth)) }
    val chosenDay =
        remember { mutableStateOf(DateTimePickerCustom.formatDay(DateTimePickerCustom.currentDay)) }

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    ModalBottomSheet(
        containerColor = CustomColor.White,
        contentColor = CustomColor.White,
        onDismissRequest = {
            showDateTimePicker.value = false
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
                                showDateTimePicker.value = false
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
                            text = stringResource(id = R.string.pickup_time),
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )
                        TextButton(
                            modifier = Modifier.weight(1f),
                            onClick = {
                                val formattedDate =
                                    "${chosenYear.value}-${chosenMonth.value}-${chosenDay.value}"
                                val formattedTime = "${chosenHour.value}:${chosenMinute.value}:00"
                                onDismissRequest(formattedDate, formattedTime)
                                showDateTimePicker.value = false
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
        sheetState = bottomSheetState,
        content = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = CustomColor.White)
            ) {
                // Date Time switch buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = CustomColor.White)
                        .padding(horizontal = AppTheme.dimens.medium),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentHeight(),
                        text = stringResource(R.string.time_of_receiving),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.primary,
                        ),
                    )

                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentHeight(),
                        text = stringResource(R.string.date_of_receiving),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.primary,
                        ),
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = CustomColor.White)
                        .padding(AppTheme.dimens.medium),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier
                            .height(AppTheme.dimens.textFieldHeight)
                            .weight(1f)
                            .background(
                                color = CustomColor.White,
                                shape = RoundedCornerShape(Constants.CORNER_RADUIES.dp)
                            )
                            .border(
                                border = BorderStroke(
                                    1.dp,
                                    color = if (isDatePickerVisible) {
                                        MaterialTheme.colorScheme.onTertiary
                                    } else {
                                        MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f)
                                    }
                                ),
                                shape = RoundedCornerShape(Constants.CORNER_RADUIES.dp)
                            )
                            .clickable { isDatePickerVisible = true },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        content = {
                            val formattedDate =
                                "${chosenYear.value}-${chosenMonth.value}-${chosenDay.value}"
                            Icon(
                                modifier = Modifier.weight(1f),
                                painter = painterResource(id = R.drawable.calendar_icon),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onTertiary
                            )
                            Text(
                                modifier = Modifier
                                    .weight(3f)
                                    .padding(horizontal = AppTheme.dimens.large)
                                    .wrapContentHeight(),
                                text = if (chosenDay.value.isNotEmpty()) {
                                    formattedDate
                                } else {
                                    "MM/DD"
                                },
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Start,
                                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                                ),
                            )
                        },
                    )
                    SpacerWidthMedium()
                    Row(
                        modifier = Modifier
                            .height(AppTheme.dimens.textFieldHeight)
                            .weight(1f)
                            .background(
                                color = CustomColor.White,
                                shape = RoundedCornerShape(Constants.CORNER_RADUIES.dp)
                            )
                            .border(
                                border = BorderStroke(
                                    1.dp,
                                    color = if (!isDatePickerVisible) {
                                        MaterialTheme.colorScheme.onTertiary
                                    } else {
                                        MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f)
                                    }
                                ),
                                shape = RoundedCornerShape(Constants.CORNER_RADUIES.dp)
                            )
                            .clickable { isDatePickerVisible = false },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        content = {
                            val formattedTime = "${chosenHour.value}:${chosenMinute.value}"
                            Icon(
                                modifier = Modifier.weight(1f),
                                painter = painterResource(id = R.drawable.calendar_icon),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onTertiary
                            )
                            Text(
                                modifier = Modifier
                                    .weight(3f)
                                    .padding(horizontal = AppTheme.dimens.large)
                                    .wrapContentHeight(),
                                text = if (chosenHour.value.isNotEmpty() && chosenMinute.value.isNotEmpty()) {
                                    formattedTime
                                } else {
                                    "--:--"
                                },
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Start,
                                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f),
                                ),
                            )
                        },
                    )
                }
                SpacerHeightLarge()
                // Check what to show date or time picker
                if (isDatePickerVisible) {
                    DateTimePickerCustom.DatePickerUI(
                        chosenYear,
                        chosenMonth,
                        chosenDay,
                    )
                } else {
                    TimePickerUI(
                        chosenHour = chosenHour,
                        chosenMinute = chosenMinute,
                    )
                }
                SpacerHeightLarge()
                SpacerHeightLarge()
            }
        }
    )
}

@Composable
fun TimePickerUI(
    chosenHour: MutableState<String>,
    chosenMinute: MutableState<String>,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = CustomColor.White)
    ) {
        TimeSelectionSection(
            onHourChosen = { chosenHour.value = it },
            onMinuteChosen = { chosenMinute.value = it }
        )
    }
}

@Composable
fun TimeSelectionSection(
    onHourChosen: (String) -> Unit,
    onMinuteChosen: (String) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .height(AppTheme.dimens.datePickerSectionHeight)
            .background(CustomColor.White)
    ) {
        InfiniteItemsPicker(
            items = hours,
            firstIndex = Int.MAX_VALUE / 2,
            onItemSelected = onHourChosen
        )

        InfiniteItemsPicker(
            items = minutes,
            firstIndex = Int.MAX_VALUE / 2,
            onItemSelected = onMinuteChosen
        )
    }
}


object DateTimePickerCustom {
    @Composable
    fun DatePickerUI(
        chosenYear: MutableState<String>,
        chosenMonth: MutableState<String>,
        chosenDay: MutableState<String>,
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(),
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = CustomColor.White)
        ) {
            DateSelectionSection(
                onYearChosen = { chosenYear.value = it },
                onMonthChosen = {
                    chosenMonth.value = if (it.isNotEmpty()) {
                        formatMonth(it.toInt() - 1)
                    } else {
                        ""
                    }
                },
                onDayChosen = {
                    chosenDay.value = if (it.isNotEmpty()) {
                        formatDay(it.toInt())
                    } else {
                        ""
                    }
                },
            )
        }
    }

    @Composable
    fun DateSelectionSection(
        onYearChosen: (String) -> Unit,
        onMonthChosen: (String) -> Unit,
        onDayChosen: (String) -> Unit,
    ) {
        val lastDayOfMonth = getLastDayOfMonth(currentYear, currentMonth)
        val nextMonth = (currentMonth + 1) % 12

        val days = rememberSaveable {
            mutableStateOf(mutableListOf<String>())
        }
        val years = rememberSaveable {
            mutableStateOf(mutableListOf<String>())
        }
        val nextMonthChoossen = remember {
            mutableStateOf(false)
        }

        LaunchedEffect(key1 = Unit, key2 = nextMonthChoossen.value) {
            if(currentMonth == Calendar.DECEMBER && nextMonthChoossen.value){
                years.value = (currentYear + 1..currentYear + 1).map { it.toString() }.toMutableList()
            }else{
                years.value = (currentYear..currentYear).map { it.toString() }.toMutableList()
            }
            if (nextMonthChoossen.value && currentDay == lastDayOfMonth) {
                days.value = ((1..1).map { String.format(Locale.US, "%02d", it) }.toMutableList())
            } else {
                if (currentDay == getLastDayOfMonth(currentYear, currentMonth)) {
                    days.value = (currentDay..currentDay).map { String.format(Locale.US, "%02d", it) }.toMutableList()
                } else if ((currentDay + 1) > getLastDayOfMonth(currentYear, currentMonth)) {
                    days.value = (currentDay..currentDay ).map { String.format(Locale.US, "%02d", it) }.toMutableList()
                } else {
                    days.value = (currentDay..currentDay + 1).map { String.format(Locale.US, "%02d", it) }.toMutableList()
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .height(AppTheme.dimens.datePickerSectionHeight)
                .background(color = CustomColor.White),
        ) {
            InfiniteItemsPicker(
                items = days.value,
                firstIndex = Int.MAX_VALUE / 2 + (currentDay - 2),
                onItemSelected = onDayChosen
            )

            InfiniteItemsPicker(
                items = monthsNames,
                firstIndex = Int.MAX_VALUE / 2 - 4 + currentMonth,
                onItemSelected = {
                    nextMonthChoossen.value = it == formatMonth(nextMonth)
                    onMonthChosen(it)
                }
            )

            InfiniteItemsPicker(
                items = years.value,
                firstIndex = currentYear,
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
            if (items.isNotEmpty()) {
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    state = listState,
                    content = {
                        items(
                            count = Int.MAX_VALUE,
                            itemContent = {
                                val index = it % items.size
                                if (items[index].isNotEmpty()) {
                                    if (it == listState.firstVisibleItemIndex + 1) {
                                        currentValue.value = items[index]
                                    }

                                    SpacerHeightSmall()

                                    Text(
                                        text = items[index],
                                        modifier = Modifier.alpha(if (it == listState.firstVisibleItemIndex + 1) 1f else 0.3f),
                                        style = MaterialTheme.typography.bodySmall,
                                        textAlign = TextAlign.Center
                                    )

                                    SpacerHeightSmall()
                                }
                            },
                        )
                    }
                )
            }

        }
    }


    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    val currentMonth = Calendar.getInstance().get(Calendar.MONTH)


    val monthsNames = listOf(
        formatMonth(currentMonth),
        if (currentDay == getLastDayOfMonth(currentYear, currentMonth)) {
            if (currentMonth == Calendar.DECEMBER) {
                formatMonth(Calendar.JANUARY)
            } else {
                formatMonth(currentMonth + 1)
            }
        } else {
            formatMonth(currentMonth)
        }
    )


    fun formatYear(year: Int): String {
        return year.toString()
    }

    fun formatMonth(month: Int): String {
        return String.format(Locale.US, "%02d", month + 1)
    }

    fun formatDay(day: Int): String {
        return String.format(Locale.US, "%02d", day)
    }


    private fun getLastDayOfMonth(year: Int, month: Int): Int {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, 1) // Set to the first day of the given month

        // Add one month and subtract one day to get the last day of the given month
        calendar.add(Calendar.MONTH, 1)
        calendar.add(Calendar.DAY_OF_MONTH, -1)

        return calendar.get(Calendar.DAY_OF_MONTH)
    }
}
