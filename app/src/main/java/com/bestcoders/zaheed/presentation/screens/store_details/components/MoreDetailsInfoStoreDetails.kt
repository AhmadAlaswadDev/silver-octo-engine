package com.bestcoders.zaheed.presentation.screens.store_details.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.extentions.checkTheActualTimeAtGivenTime
import com.bestcoders.zaheed.core.extentions.getCurrentDayName
import com.bestcoders.zaheed.core.extentions.removePadding
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.domain.model.products.StoreDetails
import com.bestcoders.zaheed.presentation.components.LineDivider
import com.bestcoders.zaheed.ui.theme.AppTheme
import org.joda.time.LocalDate

@Composable
fun MoreDetailsInfoStoreDetails(
    storeDetails: StoreDetails,
    onReturnPolicyClick: (storeReturnPolicy: String) -> Unit
) {
    var workTimeExpanded by rememberSaveable { mutableStateOf(false) }
    val workTimeContentHeight by animateDpAsState(
        if (workTimeExpanded) AppTheme.dimens.workTimeExpandedStoreDetailsHeight else 0.dp,
        label = "workTimeExpanded"
    )

    var moreDetailsExpanded by rememberSaveable { mutableStateOf(false) }
    val moreDetailsHeight by animateDpAsState(
        if (moreDetailsExpanded) {
            AppTheme.dimens.moreDetailsStoreDetailsHeight + workTimeContentHeight
        } else 0.dp,
        label = "expanded"
    )

    val storeTime = remember {
        derivedStateOf {
            val filteredList = storeDetails.workingHours.filter {
                it.day.equals(
                    LocalDate().getCurrentDayName(),
                    ignoreCase = true
                )
            }
            if (filteredList.isNotEmpty()) {
                filteredList[0]
            } else {
                null
            }
        }
    }


    val isStoreOpen =
        if (storeTime.value?.phase1from != null &&
            storeTime.value!!.phase1from.isNotEmpty()
        ) {
            LocalDate().checkTheActualTimeAtGivenTime(
                startTime = storeTime.value!!.phase1from,
                endTime = storeTime.value!!.phase1to
            ) || LocalDate().checkTheActualTimeAtGivenTime(
                startTime = storeTime.value!!.phase2from,
                endTime = storeTime.value!!.phase2to
            )
        } else {
            false
        }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .removePadding(-AppTheme.dimens.paddingHorizontal),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppTheme.dimens.moreDetailsHeaderStoreDetailsHeight)
                    .padding(horizontal = AppTheme.dimens.paddingHorizontal),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    Text(
                        text = stringResource(R.string.more_details),
                        overflow = TextOverflow.Clip,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            textAlign = TextAlign.Start,
                            color = MaterialTheme.colorScheme.primary
                        ),
                    )
                    IconButton(
                        modifier = Modifier.size(AppTheme.dimens.moreDetailsStoreDetailsIconSize),
                        onClick = {
                            moreDetailsExpanded = !moreDetailsExpanded
                        },
                        content = {
                            Icon(
                                modifier = Modifier.fillMaxSize(),
                                imageVector = if (moreDetailsExpanded) {
                                    Icons.Default.KeyboardArrowUp
                                } else {
                                    Icons.Default.KeyboardArrowDown
                                },
                                contentDescription = null
                            )
                        },
                    )
                },
            )
            LineDivider()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(moreDetailsHeight),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    // Address Info
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(AppTheme.dimens.moreDetailsHeaderStoreDetailsHeight)
                            .padding(horizontal = AppTheme.dimens.paddingHorizontal),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Icon(
                                modifier = Modifier
                                    .weight(0.09f)
                                    .size(AppTheme.dimens.moreDetailsStoreDetailsIconSize),
                                painter = painterResource(id = R.drawable.location_icon),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onTertiary
                            )
                            Text(
                                modifier = Modifier.weight(1f),
                                text = storeDetails!!.address + " (" + storeDetails.distance + stringResource(id = R.string.km) + ")",
                                overflow = TextOverflow.Clip,
                                maxLines = 1,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Start,
                                    color = MaterialTheme.colorScheme.primary
                                ),
                            )
                        },
                    )
                    LineDivider()
                    // Work Time
                    if (!workTimeExpanded) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(AppTheme.dimens.moreDetailsHeaderStoreDetailsHeight)
                                .padding(horizontal = AppTheme.dimens.paddingHorizontal),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            content = {
                                Icon(
                                    modifier = Modifier
                                        .weight(0.1f)
                                        .size(AppTheme.dimens.moreDetailsStoreDetailsIconSize),
                                    painter = painterResource(id = R.drawable.clock_icon),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onTertiary
                                )
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = if (isStoreOpen) {
                                        stringResource(R.string.open)
                                    } else {
                                        stringResource(R.string.close)
                                    },
                                    overflow = TextOverflow.Clip,
                                    maxLines = 1,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        textAlign = TextAlign.Start,
                                        color = if (isStoreOpen) {
                                            MaterialTheme.colorScheme.onSecondary
                                        } else {
                                            MaterialTheme.colorScheme.onPrimary
                                        }
                                    ),
                                )
                                IconButton(
                                    modifier = Modifier
                                        .weight(0.1f)
                                        .size(AppTheme.dimens.moreDetailsStoreDetailsIconSize),
                                    onClick = {
                                        workTimeExpanded = !workTimeExpanded
                                    },
                                    content = {
                                        Icon(
                                            modifier = Modifier.fillMaxSize(),
                                            imageVector = if (workTimeExpanded) {
                                                Icons.Default.KeyboardArrowUp
                                            } else {
                                                Icons.Default.KeyboardArrowDown
                                            },
                                            contentDescription = null
                                        )
                                    },
                                )
                            },
                        )
                    } else {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(workTimeContentHeight)
                                .padding(horizontal = AppTheme.dimens.paddingHorizontal),
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            verticalAlignment = Alignment.Top,
                            content = {
                                Icon(
                                    modifier = Modifier
                                        .weight(0.1f)
                                        .fillMaxWidth()
                                        .size(AppTheme.dimens.moreDetailsStoreDetailsIconSize),
                                    painter = painterResource(id = R.drawable.clock_icon),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onTertiary
                                )
                                Column(
                                    modifier = Modifier
                                        .weight(1f)
                                        .align(Alignment.Top)
                                        .fillMaxWidth()
                                        .wrapContentHeight(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Top,
                                    content = {
                                        storeDetails!!.workingHours.forEach { workingHours ->
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                horizontalArrangement = Arrangement.Start,
                                                verticalAlignment = Alignment.CenterVertically,
                                                content = {
                                                    Text(
                                                        modifier = Modifier.weight(1f),
                                                        text = workingHours.day + " ",
                                                        overflow = TextOverflow.Clip,
                                                        maxLines = 1,
                                                        style = MaterialTheme.typography.bodySmall.copy(
                                                            fontWeight = FontWeight.SemiBold,
                                                            textAlign = TextAlign.Start,
                                                            color = MaterialTheme.colorScheme.primary
                                                        ),
                                                    )
                                                    if (workingHours.phase1from.isNotEmpty() && workingHours.phase1to.isNotEmpty()) {
                                                        Text(
                                                            modifier = Modifier.weight(1f),
                                                            text = workingHours.phase1from + " - " + workingHours.phase1to + " &",
                                                            overflow = TextOverflow.Clip,
                                                            maxLines = 1,
                                                            style = MaterialTheme.typography.bodySmall.copy(
                                                                fontWeight = FontWeight.SemiBold,
                                                                textAlign = TextAlign.Start,
                                                                color = MaterialTheme.colorScheme.primary
                                                            ),
                                                        )
                                                    } else {
                                                        CloseText(modifier = Modifier.weight(1f))
                                                    }
                                                    if (workingHours.phase2from.isNotEmpty() && workingHours.phase2to.isNotEmpty()) {
                                                        Text(
                                                            modifier = Modifier.weight(1f),
                                                            text = " " + workingHours.phase2from + "-" + workingHours.phase2to,
                                                            overflow = TextOverflow.Clip,
                                                            maxLines = 1,
                                                            style = MaterialTheme.typography.bodySmall.copy(
                                                                fontWeight = FontWeight.SemiBold,
                                                                textAlign = TextAlign.Start,
                                                                color = MaterialTheme.colorScheme.primary
                                                            ),
                                                        )
                                                    } else {
                                                        CloseText(modifier = Modifier.weight(1f))
                                                    }
                                                },
                                            )
                                        }
                                    }
                                )
                                IconButton(
                                    modifier = Modifier
                                        .weight(0.1f)
                                        .fillMaxWidth()
                                        .size(AppTheme.dimens.moreDetailsStoreDetailsIconSize),
                                    onClick = {
                                        workTimeExpanded = !workTimeExpanded
                                    },
                                    content = {
                                        Icon(
                                            modifier = Modifier.fillMaxSize(),
                                            imageVector = if (workTimeExpanded) {
                                                Icons.Default.KeyboardArrowUp
                                            } else {
                                                Icons.Default.KeyboardArrowDown
                                            },
                                            contentDescription = null
                                        )
                                    },
                                )
                            },
                        )
                    }
                    LineDivider()
                    // Return Policy
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(AppTheme.dimens.moreDetailsHeaderStoreDetailsHeight)
                            .padding(horizontal = AppTheme.dimens.paddingHorizontal)
                            .clickable {
                                if (moreDetailsExpanded) {
                                    onReturnPolicyClick(storeDetails!!.refundPolicy)
                                }
                            },
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Icon(
                                modifier = Modifier
                                    .weight(0.1f)
                                    .size(AppTheme.dimens.moreDetailsStoreDetailsIconSize),
                                painter = painterResource(id = R.drawable.arrows_counter_clockwise_icon),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onTertiary
                            )
                            Text(
                                modifier = Modifier.weight(1f),
                                text = stringResource(R.string.return_policy),
                                overflow = TextOverflow.Clip,
                                maxLines = 1,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    textAlign = TextAlign.Start,
                                    color = MaterialTheme.colorScheme.primary
                                ),
                            )
                            Icon(
                                modifier = Modifier
                                    .weight(0.1f)
                                    .size(AppTheme.dimens.moreDetailsStoreDetailsIconSize)
                                    .graphicsLayer(
                                        rotationY = when (Constants.DEFAULT_LANGUAGE) {
                                            Constants.SAUDI_LANGUAGE_CODE -> 180f
                                            else -> 0f
                                        }
                                    ),
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = null,
                            )
                        },
                    )
                    LineDivider()
                },
            )
        },
    )
}

@Composable
fun CloseText(modifier: Modifier) {
    Text(
        modifier = modifier,
        text = stringResource(id = R.string.close),
        overflow = TextOverflow.Clip,
        maxLines = 1,
        style = MaterialTheme.typography.bodySmall.copy(
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Start,
            color = MaterialTheme.colorScheme.onPrimary
        ),
    )
}
