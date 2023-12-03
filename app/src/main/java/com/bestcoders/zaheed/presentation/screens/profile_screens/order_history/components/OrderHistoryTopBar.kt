package com.bestcoders.zaheed.presentation.screens.profile_screens.order_history.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.presentation.components.PrimaryTextField
import com.bestcoders.zaheed.ui.theme.AppTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun OrderHistoryTopBar(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    onFilterClick: () -> Unit,
    onSearchValueChange: (String) -> Unit,
) {
    var searchValue by remember { mutableStateOf("") }

    var countdownJob: Job? by remember { mutableStateOf(null) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(AppTheme.dimens.orderHistoryTapBarHeight)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(
                        start = AppTheme.dimens.paddingHorizontal,
                        end = AppTheme.dimens.paddingHorizontal,
                        top = AppTheme.dimens.paddingVertical
                    )
                    .background(MaterialTheme.colorScheme.background)
                    .height(AppTheme.dimens.textFieldHeight),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
            ) {
                IconButton(
                    onClick = onBackClicked,
                    modifier = Modifier.size(AppTheme.dimens.topBarIconSize)
                ) {
                    Icon(
                        modifier = Modifier
                            .size(AppTheme.dimens.topBarIconSize)
                            .graphicsLayer(
                                rotationY = when (Constants.DEFAULT_LANGUAGE) {
                                    Constants.SAUDI_LANGUAGE_CODE -> 180f
                                    else -> 0f
                                }
                            ),
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = null
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxHeight(1f)
                        .fillMaxWidth(0.8f)
                        .align(Alignment.CenterVertically),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center),
                        text = stringResource(id = R.string.order_history),
                        style = MaterialTheme.typography.displaySmall.copy(
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                        ),
                        textAlign = TextAlign.Center,
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(AppTheme.dimens.textFieldHeight)
                    .padding(horizontal = AppTheme.dimens.paddingHorizontal),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                PrimaryTextField(
                    modifier = Modifier.weight(1.5f),
                    text = searchValue,
                    placeHolderTextStyle = MaterialTheme.typography.bodySmall,
                    textStyle = MaterialTheme.typography.bodySmall,
                    placeHolder = stringResource(R.string.search_by_orders),
                    onValueChange = { value ->
                        searchValue = value
                        // Cancel any existing countdown timer when a new letter is typed
                        countdownJob?.cancel()
                        // Start a new countdown timer for 500 milliseconds
                        countdownJob = MainScope().launch {
                            delay(500) // Adjust this delay according to your preference
                            // Trigger the request after the delay if the user didn't type within this period
                            if (value == searchValue) {
                                // Call the request function here
                                onSearchValueChange(searchValue)
                            }
                        }
                    },
                    leadingIcon = {
                        Image(
                            painter = painterResource(R.drawable.search_icon),
                            contentDescription = stringResource(R.string.search_icon),
                            modifier = Modifier
                                .size(AppTheme.dimens.searchFieldIconSize)
                                .fillMaxHeight()
                                .align(Alignment.CenterVertically)
                        )
                    },
                )
                Icon(
                    modifier = Modifier
                        .weight(0.3f)
                        .size(AppTheme.dimens.homeTopBarIconSize)
                        .clickable { onFilterClick() },
                    painter = painterResource(id = R.drawable.filter_icon),
                    contentDescription = null,
                )
            }
        }
    )
}