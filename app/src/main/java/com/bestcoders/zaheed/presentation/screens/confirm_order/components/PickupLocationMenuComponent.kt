package com.bestcoders.zaheed.presentation.screens.confirm_order.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.extentions.removePadding
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.domain.model.track.PickupPoint
import com.bestcoders.zaheed.presentation.components.LineDivider
import com.bestcoders.zaheed.ui.theme.AppTheme
import com.bestcoders.zaheed.ui.theme.CustomColor

@Composable
fun PickupLocationMenuComponent(
    modifier: Modifier = Modifier,
    pickupLocations: List<PickupPoint>,
    selectedPickupLocation: MutableState<PickupPoint?>,
    onPickupLocationClick: () -> Unit,
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.Start,
        content = {
            Text(
                modifier = Modifier.wrapContentHeight(),
                text = stringResource(R.string.pickup_location),
                overflow = TextOverflow.Clip,
                maxLines = 1,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.primary
                ),
            )
            StorePickupLocationDropdown(
                pickupLocations = pickupLocations,
                onPickupLocationClick = onPickupLocationClick,
                onItemSelected = { selectedOption ->
                    selectedPickupLocation.value = selectedOption
                }
            )
            LineDivider(modifier = Modifier.removePadding(-AppTheme.dimens.paddingHorizontal))
        }
    )
}


@Composable
fun StorePickupLocationDropdown(
    pickupLocations: List<PickupPoint>,
    onItemSelected: (PickupPoint) -> Unit,
    onPickupLocationClick: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionAddress by remember {
        mutableStateOf(
            pickupLocations.firstOrNull()?.address ?: ""
        )
    }
    var selectedOptionStore by remember {
        mutableStateOf(
            pickupLocations.firstOrNull()?.store ?: ""
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(Constants.CORNER_RADUIES))
            .background(MaterialTheme.colorScheme.secondaryContainer.copy(0.08f))
            .padding(horizontal = AppTheme.dimens.paddingHorizontal)
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(AppTheme.dimens.small)
                .clickable {
                    onPickupLocationClick()
                }

        ) {
            Box(
                modifier = Modifier
                    .weight(0.2f)
                    .align(Alignment.CenterVertically)
                    .size(AppTheme.dimens.pickupLocationBoxIconConfirmOrderSize)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(Constants.CORNER_RADUIES)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.marker_icon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier.size(AppTheme.dimens.pickupLocationIconConfirmOrderSize)
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = selectedOptionAddress,
                    maxLines = 2,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.primary,
                        lineHeight = 20.sp
                    )
                )
                Text(
                    text = selectedOptionStore,
                    maxLines = 2,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.primary,
                        lineHeight = 20.sp
                    )
                )
            }
            Icon(
                modifier = Modifier
                    .weight(0.1f)
                    .fillMaxSize()
                    .clickable { expanded = true },
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }

        DropdownMenu(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(Constants.CORNER_RADUIES))
                .background(CustomColor.White)
                .padding(horizontal = AppTheme.dimens.paddingHorizontal),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            pickupLocations.forEach { option ->
                DropdownMenuItem(
                    modifier = Modifier.wrapContentWidth(),
                    onClick = {
                        selectedOptionAddress = option.address
                        selectedOptionStore = option.store!!
                        onItemSelected(option)
                        expanded = false
                    },
                    text = { Text(text = option.address) }
                )
                LineDivider(
                    modifier = Modifier.removePadding(-AppTheme.dimens.paddingHorizontal)
                )
            }
        }
    }
}
