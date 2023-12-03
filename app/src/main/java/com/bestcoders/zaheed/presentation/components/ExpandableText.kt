package com.bestcoders.zaheed.presentation.components


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle

/**
 * @param modifier use this to add padding and such
 * @param longText is the Text that is to long and need to be displayed that has more than [minimizedMaxLines]
 * @param minimizedMaxLines (optional) the minimum amount of text lines to be visible in non-expanded state
 * @param textAlign (optional) defaults to [TextAlign.Start] unless overridden, try [TextAlign.Justify]
 * @param expandHint (optional) this text is appended to the [longText] before expanding and become clickable
 * @param shrinkHint (optional) this text is appended to the [longText] after expanding and become clickable
 * @param clickColor (optional) denotes the color of the clickable [expandHint] & [shrinkHint] strings
 * */
@Composable
fun ExpandableText(
    modifier: Modifier = Modifier,
    longText: String,
    minimizedMaxLines: Int = 4,
    textAlign: TextAlign = TextAlign.Start,
    expandHint: String = "… Show More",
    shrinkHint: String = "… Show Less",
    clickColor: Color = MaterialTheme.colorScheme.primary,
    maxLines:Int = 4,
) {
    var isExpanded by remember { mutableStateOf(value = false) }
    var textLayoutResultState by remember { mutableStateOf<TextLayoutResult?>(value = null) }
    var adjustedText by remember { mutableStateOf(value = longText) }
    val overflow = textLayoutResultState?.hasVisualOverflow ?: false
    val showOverflow = remember { mutableStateOf(value = false) }
    val showMore = " $expandHint"
    val showLess = " $shrinkHint"

    LaunchedEffect(textLayoutResultState) {
        if (textLayoutResultState == null) return@LaunchedEffect
        if (!isExpanded && overflow) {
            showOverflow.value = true
            val lastCharIndex = textLayoutResultState!!.getLineEnd(lineIndex = minimizedMaxLines - 1)
            adjustedText = longText
                .substring(startIndex = 0, endIndex = lastCharIndex)
                .dropLast(showMore.length)
                .dropLastWhile { it == ' ' || it == '.' }
        }
    }
    val annotatedText = buildAnnotatedString {
        if (isExpanded) {
            append(longText)
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize =MaterialTheme.typography.bodySmall.fontSize
                )
            ) {
                pushStringAnnotation(tag = "showLess", annotation = "showLess")
                append(showLess)
                addStyle(
                    style = SpanStyle(
                        color = clickColor,
                        fontSize = MaterialTheme.typography.bodySmall.fontSize
                    ),
                    start = longText.length,
                    end = longText.length + showMore.length
                )
                pop()
            }
        } else {
            append(adjustedText)
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize
                )
            ) {
                if (showOverflow.value) {
                    pushStringAnnotation(tag = "showMore", annotation = "showMore")
                    append(showMore)
                    addStyle(
                        style = SpanStyle(
                            color = clickColor,
                            fontSize = MaterialTheme.typography.bodySmall.fontSize
                        ),
                        start = adjustedText.length,
                        end = adjustedText.length + showMore.length
                    )
                    pop()
                }
            }
        }

    }
    Box(modifier = modifier) {
        ClickableText(
            text = annotatedText,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = textAlign
            ),
            maxLines = if (isExpanded) Int.MAX_VALUE else maxLines,
            onTextLayout = { textLayoutResultState = it },
            onClick = { offset ->
                annotatedText.getStringAnnotations(
                    tag = "showLess",
                    start = offset,
                    end = offset + showLess.length
                ).firstOrNull()?.let {
                    isExpanded = !isExpanded
                }
                annotatedText.getStringAnnotations(
                    tag = "showMore",
                    start = offset,
                    end = offset + showMore.length
                ).firstOrNull()?.let {
                    isExpanded = !isExpanded
                }
            }
        )
    }
}