package com.bestcoders.zaheed.presentation.screens.track.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.extentions.checkTheActualTimeAtGivenTime
import com.bestcoders.zaheed.core.extentions.convertToReadyToPickupDateFormat
import com.bestcoders.zaheed.core.extentions.getCurrentDayName
import com.bestcoders.zaheed.core.extentions.navigateToLocationInGoogleMaps
import com.bestcoders.zaheed.domain.model.track.Order
import com.bestcoders.zaheed.domain.model.track.PickupPoint
import com.bestcoders.zaheed.domain.model.track.Track
import com.bestcoders.zaheed.ui.theme.AppTheme
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime

@Composable
fun OrderReadyForPickupComponent(pickupPoint: PickupPoint, track: Track, order: Order) {

    val context = LocalContext.current
    val storeTime = remember {
        derivedStateOf {
            pickupPoint.openDays!!.filter {
                it.day.equals(
                    LocalDate().getCurrentDayName(),
                    ignoreCase = true
                )
            }[0]
        }
    }
    val isStoreOpen = if (storeTime.value.phase1from.isNotEmpty()) {
        LocalDate().checkTheActualTimeAtGivenTime(
            startTime = storeTime.value.phase1from,
            endTime = storeTime.value.phase1to
        ) || LocalDate().checkTheActualTimeAtGivenTime(
            startTime = storeTime.value.phase2from,
            endTime = storeTime.value.phase2to
        )
    } else {
        false
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.small),
        content = {
            OrderStatusImageComponent(
                image = R.drawable.success_track_order_icon,
                showImage = track.isOrderReadyToPickUp,
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                content = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        content = {
                            OrderStatusTextComponent(
                                text = R.string.ready_for_pickup,
                                isActive = track.isOrderReadyToPickUp
                            )
                            Icon(
                                modifier = Modifier
                                    .size(AppTheme.dimens.trackOrderLocationIconSize)
                                    .padding(horizontal = AppTheme.dimens.paddingHorizontal)
                                    .clickable {
                                        navigateToLocationInGoogleMaps(context, pickupPoint.address)
                                    },
                                painter = painterResource(id = R.drawable.map_trifold),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        },
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.small),
                        content = {
                            OrderStatusSubImageComponent(
                                image = R.drawable.calendar_icon,
                                isActive = track.isOrderReadyToPickUp,
                            )
                            Log.e(
                                "KUAJSFGHUAJSF",
                                "OrderReadyForPickupComponent: ${track.orderReadyToPickUpAt}",

                            )
                            Text(
                                /*
                                LocalDateTime().convertToReadyToPickupDateFormat("2023-5-11 12:05:55") + "-" +
                                        LocalDateTime().convertToReadyToPickupDateFormat("2023-5-13 5:52:45")
                                 */
                                text = if (!order.preferredTimeToPickUp.isNullOrEmpty() && !track.orderReadyToPickUpAt.isNullOrEmpty()) {
                                    LocalDateTime().convertToReadyToPickupDateFormat(track.orderReadyToPickUpAt) + "-" +
                                            LocalDateTime().convertToReadyToPickupDateFormat(order.preferredTimeToPickUp)
                                } else {
                                    "--:--"
                                },
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.secondary.copy(
                                        alpha = 0.5f
                                    ),
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.small),
                        content = {
                            OrderStatusSubImageComponent(
                                image = R.drawable.clock_icon,
                                isActive = track.isOrderReadyToPickUp,
                            )
                            Text(
                                text = if (isStoreOpen) {
                                    storeTime.value.phase1from + "-" + storeTime.value.phase1to + " " +
                                            storeTime.value.phase2from + "-" + storeTime.value.phase2to
                                } else {
                                    stringResource(id = R.string.closed)
                                },
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.secondary.copy(
                                        alpha = 0.5f
                                    ),
                                    fontWeight = FontWeight.SemiBold,
                                    lineHeight = 20.sp
                                )
                            )
                        }
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.small),
                        content = {
                            OrderStatusSubImageComponent(
                                image = R.drawable.marker_border_icon,
                                isActive = track.isOrderReadyToPickUp,
                            )
                            Text(
                                text = pickupPoint.address,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MaterialTheme.colorScheme.secondary.copy(
                                        alpha = 0.5f
                                    ),
                                    fontWeight = FontWeight.SemiBold,
                                    lineHeight = 20.sp
                                )
                            )
                        }
                    )
                }
            )

        },
    )
}