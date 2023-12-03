package com.bestcoders.zaheed.presentation.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.presentation.components.PrimaryTextField
import com.bestcoders.zaheed.presentation.components.SpacerHeightMediumLarge
import com.bestcoders.zaheed.presentation.components.SpacerWidthLarge
import com.bestcoders.zaheed.presentation.components.SpacerWidthSmall
import com.bestcoders.zaheed.ui.theme.AppTheme
import com.bestcoders.zaheed.ui.theme.CustomColor

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    savedMoneyLabel: String,
    onLocationIconClick: () -> Unit,
    navigateToSearchScreen: () -> Unit,
    onFilterClick: () -> Unit
) {
    val textValue by remember {
        derivedStateOf { "" }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(AppTheme.dimens.topBarHeight)
            .background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = AppTheme.dimens.paddingHorizontal
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Image(
                modifier = Modifier
                    .weight(1f)
                    .width(AppTheme.dimens.homeLogoWidth)
                    .height(AppTheme.dimens.homeLogoHeight),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = stringResource(R.string.logo)
            )
            SavedMoney(
                modifier = Modifier.weight(2.5f),
                label = savedMoneyLabel + Constants.MAIN_CURENCY
            )
            HomeTopBarActionIcons(
                modifier = Modifier.weight(1f),
                onLocationClick = { onLocationIconClick() },
                onFilterClick = onFilterClick,
            )
        }
        SpacerHeightMediumLarge()
        PrimaryTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(AppTheme.dimens.textFieldHeight)
                .padding(
                    horizontal = AppTheme.dimens.paddingHorizontal
                )
                .clickable {
                    navigateToSearchScreen()
                },
            text = textValue,
            placeHolderTextStyle = MaterialTheme.typography.bodySmall,
            textStyle = MaterialTheme.typography.bodySmall,
            placeHolder = stringResource(R.string.search_products_or_stores),
            leadingIcon = {
                Image(
                    painter = painterResource(R.drawable.search_icon),
                    contentDescription = stringResource(R.string.search_icon),
                    modifier = Modifier.size(AppTheme.dimens.searchFieldIconSize)
                )
            },
            enabled = false
        )
    }

}


@Composable
fun HomeTopBarActionIcons(
    modifier: Modifier = Modifier,
    onLocationClick: () -> Unit,
    onFilterClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
    ) {
        IconButton(
            onClick = { onLocationClick() },
            modifier = Modifier.size(AppTheme.dimens.homeTopBarIconSize)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.location_icon),
                contentDescription = stringResource(
                    R.string.location_icon
                )
            )
        }
        SpacerWidthLarge()
        IconButton(
            onClick = onFilterClick,
            modifier = Modifier.size(AppTheme.dimens.homeTopBarIconSize)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.filter_icon),
                contentDescription = stringResource(R.string.filter_icon),
            )
        }
        SpacerWidthLarge()
    }
}

@Composable
fun SavedMoney(modifier: Modifier = Modifier, label: String) {
    Row(
        modifier = modifier
            .wrapContentSize()
            .height(AppTheme.dimens.savedMoneyHeight)
            .background(
                MaterialTheme.colorScheme.onSecondary,
                RoundedCornerShape(100.dp)
            )
            .padding(
                horizontal = AppTheme.dimens.large,
            )
            .wrapContentWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Image(
            modifier = Modifier.size(AppTheme.dimens.saveMoneyIconSize),
            painter = painterResource(id = R.drawable.coin_icon),
            contentDescription = stringResource(R.string.coin_icon)
        )
        SpacerWidthSmall()
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                color = CustomColor.White
            ),
        )
    }
}