package com.bestcoders.zaheed.presentation.screens.favorite.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.presentation.components.LineDivider
import com.bestcoders.zaheed.presentation.components.PrimarySelector
import com.bestcoders.zaheed.presentation.components.SpacerHeightSmall
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun FavoriteTopBar(
    modifier: Modifier = Modifier,
    storesProductsSelector: MutableState<Int>,
) {
    val storesProductsOptions =
        listOf(stringResource(R.string.the_stores), stringResource(R.string.the_products))

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
                .padding(
                    start = AppTheme.dimens.paddingHorizontal,
                    end = AppTheme.dimens.paddingHorizontal,
                    top = AppTheme.dimens.paddingVertical
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier.wrapContentWidth(),
                text = stringResource(id = R.string.favorite),
                style = MaterialTheme.typography.displaySmall.copy(
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold,
                ),
                textAlign = TextAlign.Start,
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