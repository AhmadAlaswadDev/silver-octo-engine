package com.bestcoders.zaheed.presentation.screens.search.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.presentation.components.LineDivider
import com.bestcoders.zaheed.presentation.components.PrimarySelector
import com.bestcoders.zaheed.presentation.components.PrimaryTextField
import com.bestcoders.zaheed.presentation.components.SpacerHeightSmall
import com.bestcoders.zaheed.ui.theme.AppTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SearchTopBar(
    modifier: Modifier = Modifier,
    onCancelClick: () -> Unit,
    storesProductsSelector: MutableState<Int>,
    onSearchValueChange: (String) -> Unit,
) {
    var searchValue by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    val storesProductsOptions =
        listOf(stringResource(R.string.the_stores), stringResource(R.string.the_products))
    var countdownJob: Job? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(AppTheme.dimens.searchTopBarHeight)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(AppTheme.dimens.textFieldHeight)
                .padding(  start = AppTheme.dimens.paddingHorizontal,
                    end = AppTheme.dimens.paddingHorizontal,
                    top = AppTheme.dimens.paddingVertical
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            PrimaryTextField(
                modifier = Modifier.weight(1.5f).focusRequester(focusRequester),
                text = searchValue,
                placeHolderTextStyle = MaterialTheme.typography.bodySmall,
                textStyle = MaterialTheme.typography.bodySmall,
                placeHolder = stringResource(R.string.search_products_or_stores),
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
            TextButton(
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxSize(),
                onClick = onCancelClick,
                contentPadding = PaddingValues(0.dp),
                content = {
                    Text(
                        text = stringResource(R.string.cancel),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.primary,
                        )
                    )
                }
            )
        }
        SpacerHeightSmall()
        PrimarySelector(
            modifier = Modifier
                .padding(horizontal = AppTheme.dimens.paddingHorizontal),
            selectedItem = storesProductsSelector,
            items = storesProductsOptions,
        )
        SpacerHeightSmall()
        LineDivider()
    }

}