package com.bestcoders.zaheed.presentation.screens.signup.components

//


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.presentation.components.SpacerHeightMedium

@Composable
fun PrivacyPoliciesButton(
    modifier: Modifier = Modifier,
    onPrivacyPoliciesClick: () -> Unit,
    onTermsConditionsClick: () -> Unit,
) {

    return  Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.by_clicking_sign_up_you_agree_to_our),
                modifier = Modifier
                    .wrapContentSize(),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.secondary
                ),
            )
            SpacerHeightMedium()
            Text(
                text = stringResource(R.string.terms_conditions),
                modifier = Modifier
                    .clickable {
                        onTermsConditionsClick()
                    }
                    .wrapContentSize(),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onPrimary,
                    textDecoration = TextDecoration.Underline
                ),
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SpacerHeightMedium()
            Text(
                text = stringResource(R.string.and),
                modifier = Modifier
                    .wrapContentSize(),

                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.secondary
                ),
            )
            SpacerHeightMedium()
            Text(
                text = stringResource(R.string.privacy_policies),
                modifier = Modifier
                    .wrapContentSize()
                    .clickable {
                        onPrivacyPoliciesClick()
                    },
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onPrimary,
                    textDecoration = TextDecoration.Underline
                ),
            )
        }
    }
}