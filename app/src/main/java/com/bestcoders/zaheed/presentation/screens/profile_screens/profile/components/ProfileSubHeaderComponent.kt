package com.bestcoders.zaheed.presentation.screens.profile_screens.profile.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.presentation.components.SpacerHeightSmall
import com.bestcoders.zaheed.ui.theme.AppTheme

@Composable
fun ProfileSubHeaderComponent(
    navigateToOrderHistoryScreen: () -> Unit,
) {
    if (Constants.userToken.isNotEmpty()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            content = {
                ProfileItemCardComponent(
                    modifier = Modifier,
                    label = stringResource(R.string.order_history),
                    icon = R.drawable.order_history_icon,
                    onClick = navigateToOrderHistoryScreen,
                )
            },
        )
    }
}

@Composable
fun ProfileItemCardComponent(modifier: Modifier, label: String, icon: Int, onClick: () -> Unit = {}) {
    Column(
        modifier = modifier
            .wrapContentWidth()
            .border(
                border = BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f)
                ),
                shape = RoundedCornerShape(Constants.CORNER_RADUIES.dp)
            )
            .padding(horizontal = AppTheme.dimens.large, vertical = AppTheme.dimens.medium)
            .clickable { onClick() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Image(
                modifier = Modifier.size(AppTheme.dimens.profileScreenIconSize),
                painter = painterResource(id = icon),
                contentDescription = null,
            )
            SpacerHeightSmall()
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.SemiBold,
                )
            )
        }
    )
}
