package com.bestcoders.zaheed.presentation.screens.confirm_order.components

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.presentation.components.SpacerHeightExtraLarge
import com.bestcoders.zaheed.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentMethodSheet(onDismiss: () -> Unit) {

    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = bottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        content = {
            Column(
                modifier = Modifier.fillMaxWidth().padding(horizontal = AppTheme.dimens.mediumLarge),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    PaymentMethodItem(
                        onItemClick = {
                            onDismiss()
                        }
                    )
                    SpacerHeightExtraLarge()
                }
            )
        },
    )

}

@Composable
fun PaymentMethodItem(
    onItemClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(AppTheme.dimens.paymentMethodRowConfirmOrderHeight)
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.08f),
                shape = RoundedCornerShape(
                    Constants.CORNER_RADUIES
                )
            )
            .clickable {
                onItemClick()
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        content = {
            Image(
                modifier = Modifier
                    .weight(0.4f)
                    .size(AppTheme.dimens.moneyIconConfirmOrderSize),
                painter = painterResource(id = R.drawable.visa_icon),
                contentScale = ContentScale.Inside,
                contentDescription = null,
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentHeight(),
                text = stringResource(R.string.online_payment),
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.primary,
                ),
            )
            Image(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(AppTheme.dimens.medium),
                painter = painterResource(id = R.drawable.payment_success_icon),
                contentDescription = null
            )
        },
    )
}