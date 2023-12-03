package com.bestcoders.zaheed.presentation.screens.profile_screens.profile.components

import android.app.LocaleManager
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import android.util.LayoutDirection
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import androidx.core.text.layoutDirection
import com.bestcoders.zaheed.MainActivity
import com.bestcoders.zaheed.R
import com.bestcoders.zaheed.core.extentions.mirror
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.presentation.screens.profile_screens.profile.ProfileEvent
import com.bestcoders.zaheed.ui.theme.AppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun ProfileScreenBody(
    onEvent: (ProfileEvent) -> Unit,
    navigateToSignInScreen: () -> Unit,
    navigateToEditProfileScreen: () -> Unit,
    navigateToUpdatePasswordScreen: () -> Unit,
    navigateToAboutUsScreen: () -> Unit,
    navigateToFAQScreen: () -> Unit,
    navigateToPrivacyPoliciesScreen: (String) -> Unit,
) {
    val context = LocalContext.current
    val showLanguageDialog = remember {
        mutableStateOf(false)
    }
    if (Constants.userToken.isEmpty()) {
        ProfileItemComponent(
            label = stringResource(id = R.string.sign_in),
            icon = R.drawable.logout_icon,
            onClick = navigateToSignInScreen
        )
    }
    if (Constants.userToken.isNotEmpty()) {
        ProfileItemComponent(
            label = stringResource(id = R.string.my_profile),
            icon = R.drawable.profile_icon,
            onClick = navigateToEditProfileScreen
        )
    }
    ProfileItemComponent(
        label = stringResource(R.string.contact_us),
        icon = R.drawable.contact_us_icon,
        onClick = { onEvent(ProfileEvent.OnContactDetailsClicked(context)) }
    )
    ProfileItemComponent(
        label = stringResource(R.string.about_us),
        icon = R.drawable.about_icon,
        onClick = navigateToAboutUsScreen
    )
    if (Constants.userToken.isNotEmpty()) {
        ProfileItemComponent(
            label = stringResource(R.string.change_password),
            icon = R.drawable.change_password_icon,
            onClick = navigateToUpdatePasswordScreen
        )
    }
    ProfileItemComponent(
        label = stringResource(R.string.terms_conditions),
        icon = R.drawable.privacy_policies_icon,
        onClick = {
            navigateToPrivacyPoliciesScreen("0")
        }
    )
    ProfileItemComponent(
        label = stringResource(R.string.privacy_policies),
        icon = R.drawable.privacy_policies_icon,
        onClick = {
            navigateToPrivacyPoliciesScreen("1")
        }
    )
    ProfileItemComponent(
        label = stringResource(R.string.change_language),
        icon = R.drawable.change_language_icon,
        onClick = {
            showLanguageDialog.value = true
        }
    )
    if (Constants.userToken.isNotEmpty()) {
        ProfileItemComponent(
            label = stringResource(R.string.faq),
            icon = R.drawable.faq_icon,
            onClick = navigateToFAQScreen
        )
    }
    if (Constants.userToken.isNotEmpty()) {
        ProfileItemComponent(
            label = stringResource(R.string.logout),
            icon = R.drawable.logout_icon,
            onClick = { onEvent(ProfileEvent.OnLogOutClicked) }
        )
    }

    if (showLanguageDialog.value) {
        LanguageDialog { newLanguage ->
            onEvent(ProfileEvent.ChangeLanguage(newLanguage, context))
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (newLanguage == Constants.ARABIC_LANGUAGE_CODE || newLanguage == Constants.SAUDI_LANGUAGE_CODE) {
                    context.getSystemService(LocaleManager::class.java).applicationLocales = LocaleList.forLanguageTags(Constants.ARABIC_LANGUAGE_CODE)
                    Constants.DEFAULT_LANGUAGE = Constants.SAUDI_LANGUAGE_CODE
                } else {
                    context.getSystemService(LocaleManager::class.java).applicationLocales = LocaleList.forLanguageTags(Constants.ENGLISH_LANGUAGE_CODE)
                    Constants.DEFAULT_LANGUAGE = Constants.ENGLISH_LANGUAGE_CODE
                }
                showLanguageDialog.value = false
            } else {
                val locale = Locale(newLanguage)
                Locale.setDefault(locale)
                val resources = context.resources
                val config: Configuration = resources.configuration
                config.setLocale(locale)
                resources.updateConfiguration(config, resources.displayMetrics)
                if (newLanguage == Constants.ARABIC_LANGUAGE_CODE || newLanguage == Constants.SAUDI_LANGUAGE_CODE) {
                    Constants.DEFAULT_LANGUAGE = Constants.SAUDI_LANGUAGE_CODE
                } else {
                    Constants.DEFAULT_LANGUAGE = Constants.ENGLISH_LANGUAGE_CODE
                }
                showLanguageDialog.value = false
                val restartIntent = Intent(context, MainActivity::class.java)
                restartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                context.startActivity(restartIntent)
            }
        }
    }
}

@Composable
fun ProfileItemComponent(
    modifier: Modifier = Modifier,
    label: String,
    icon: Int,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(AppTheme.dimens.profileItemHeight)
            .border(
                border = BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.2f)
                ),
                shape = RoundedCornerShape(Constants.CORNER_RADUIES.dp)
            )
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(AppTheme.dimens.medium),
        content = {
            Image(
                modifier = Modifier
                    .size(AppTheme.dimens.profileScreenIconSize)
                    .weight(1f),
                painter = painterResource(id = icon),
                contentDescription = null,
            )
            Text(
                modifier = Modifier.weight(5f),
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.SemiBold,
                )
            )
            Icon(
                modifier = Modifier
                    .weight(1f)
                    .size(AppTheme.dimens.topBarIconSize)
                    .mirror(),
                imageVector = Icons.Rounded.KeyboardArrowRight,
                contentDescription = null
            )
        },
    )
}


