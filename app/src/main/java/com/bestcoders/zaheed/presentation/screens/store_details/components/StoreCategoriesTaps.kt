package com.bestcoders.zaheed.presentation.screens.store_details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.presentation.components.PrimaryButton
import com.bestcoders.zaheed.ui.theme.AppTheme
import com.bestcoders.zaheed.ui.theme.CustomColor

@Composable
fun StoreCategoriesTaps(
    tabNames: () -> List<String>,
    selectedTab: () -> MutableState<Int>,
    list: () -> SnapshotStateList<String>,
    sortedList: () -> SnapshotStateList<List<String>?>
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(AppTheme.dimens.paddingHorizontal),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.storeCategoriesTapsVerticalArrangement)
    ) {
        TabRow(
            modifier = Modifier.fillMaxWidth(),
            selectedTabIndex = selectedTab().value,
            divider = {},
            tabs = {
                tabNames().forEachIndexed { index, tabName ->
                    Tab(
                        selected = selectedTab().value == index,
                        onClick = {
                            selectedTab().value = index
                            // Sort the productDetailsDataResponse based on the selected tap
//                            sortedList() = when (index) {
//                                0 -> list().sortedBy { it }
//                                1 -> list().filter { it == tabName }
//                                2 -> list().filter { it == tabName }
//                                3 -> list().filter { it == tabName }
//                                else -> list() // Default case: do not sort
//                            }
                        }
                    ) {
                        Text(
                            text = tabName, style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.primary,
                            )
                        )
                    }
                }
            },
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            content = {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        Text(
                            modifier = Modifier,
                            text = "209 ",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.secondary,
                            )
                        )
                        Text(
                            modifier = Modifier,
                            text = stringResource(R.string.results),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.secondary.copy(
                                    alpha = 0.4f
                                ),
                            )
                        )
                    }
                )
                PrimaryButton(
                    modifier = Modifier
                        .height(AppTheme.dimens.filterButtonStoreDetailsHeight)
                        .width(AppTheme.dimens.filterButtonStoreDetailsWidth),
                    color = CustomColor.White,
                    borderColor = MaterialTheme.colorScheme.onSecondary,
                    borderStroke = 2f,
                    radius = 50f,
                    text = stringResource(id = R.string.filter),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onSecondary,
                    ),
                    icon = R.drawable.filter_icon,
                    iconColor = MaterialTheme.colorScheme.onSecondary,
                    iconSize = AppTheme.dimens.filterButtonIconStoreDetailsSize,
                    onClick = {}
                )
            },
        )
    }

}