package com.bestcoders.zaheed.presentation.components

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bestcoders.zaheed.core.util.Constants
import com.bestcoders.zaheed.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimaryTextField(
    modifier: Modifier = Modifier,
    text: String,
    placeHolder: String = "",
    borderStroke: Float = 1f,
    radius: Float = 10f,
    errorMessage: String? = null,
    showError: Boolean = false,
    borderColor: Color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f),
    background: Color = Color.Transparent,
    textFieldColors: TextFieldColors? = null,
    onValueChange: (String) -> Unit = {},
    textStyle: TextStyle = MaterialTheme.typography.bodySmall.copy(
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Start,
    ),
    placeHolderTextStyle: TextStyle = MaterialTheme.typography.bodySmall.copy(
        color = MaterialTheme.colorScheme.tertiary,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Start,
    ),
    singleLine: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyboardActions: KeyboardActions = KeyboardActions(),
    onFocusChange: (FocusState) -> Unit = {},
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
    label: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    interactionSource: MutableInteractionSource = MutableInteractionSource(),
    underLineColor: Color = Color.Transparent
) {
    val dimens = AppTheme.dimens

    ConstraintLayout(modifier = modifier) {
        val (labelRef, mobileNumberField, errorTextRef) = createRefs()
        if (!label.isNullOrEmpty()) {
            CompositionLocalProvider(
                LocalLayoutDirection provides if (Constants.DEFAULT_LANGUAGE == Constants.SAUDI_LANGUAGE_CODE || Constants.DEFAULT_LANGUAGE == Constants.ARABIC_LANGUAGE_CODE) {
                    LayoutDirection.Rtl
                } else {
                    LayoutDirection.Ltr
                }
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .constrainAs(labelRef) {
                            top.linkTo(parent.top, margin = dimens.small)
                            bottom.linkTo(mobileNumberField.top, margin = dimens.small)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                    text = label,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    ),
                    textAlign = TextAlign.Start,
                )
            }
        }
        BasicTextField(
            value = text,
            onValueChange = onValueChange,
            modifier = Modifier
                .clip(RoundedCornerShape(radius.dp))
                .border(
                    BorderStroke(
                        borderStroke.dp, when {
                            showError && !errorMessage.isNullOrEmpty() ->
                                MaterialTheme.colorScheme.errorContainer

                            else -> borderColor
                        }
                    ), RoundedCornerShape(radius.dp)
                )
                .fillMaxWidth()
                .constrainAs(mobileNumberField) {
                    top.linkTo(labelRef.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .height(AppTheme.dimens.textFieldHeight)
                .onFocusChanged { onFocusChange(it) },
            visualTransformation = visualTransformation,
            interactionSource = interactionSource,
            enabled = enabled,
            singleLine = singleLine,
            keyboardActions = keyboardActions,
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType, imeAction = imeAction
            ),
            textStyle = textStyle,
        ) { innerTextField ->
            TextFieldDefaults.DecorationBox(
                value = text,
                singleLine = singleLine,
                shape = RoundedCornerShape(radius.dp),
                placeholder = { Text(placeHolder, style = placeHolderTextStyle) },
                enabled = enabled,
                visualTransformation = visualTransformation,
                trailingIcon = trailingIcon,
                leadingIcon = leadingIcon,
                colors = textFieldColors ?: TextFieldDefaults.colors(
                    focusedContainerColor = background,
                    unfocusedContainerColor = background,
                    disabledContainerColor = background,
                    focusedIndicatorColor = underLineColor,
                    unfocusedIndicatorColor = underLineColor,
                ),
                interactionSource = interactionSource,
                contentPadding = PaddingValues(horizontal = 10.dp),
                innerTextField = innerTextField
            )
        }
        if (showError && !errorMessage.isNullOrEmpty()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(errorTextRef) {
                        top.linkTo(mobileNumberField.bottom, margin = dimens.small)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                text = errorMessage,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onPrimary
                ),
                textAlign = TextAlign.Start
            )
        }
    }
}

