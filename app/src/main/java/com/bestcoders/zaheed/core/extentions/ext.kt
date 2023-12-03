package com.bestcoders.zaheed.core.extentions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import android.util.LayoutDirection
import android.util.Patterns
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.offset
import androidx.core.text.layoutDirection
import com.bestcoders.zaheed.core.util.Constants
import com.google.gson.Gson
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import org.joda.time.LocalTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter
import java.util.Locale

// convert string to color
val String.color
    get() = Color(android.graphics.Color.parseColor(this))


// validate email
fun String.isValidEmail() =
    !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()

// Remove Zero After Comma
fun String.removeZerosAfterComma(): String {
    val parts = this.split(".")
    if (parts.size == 2) {
        val decimalPart = parts[1].trimStart('0')
        if (decimalPart.isNotEmpty()) {
            return "${parts[0]},$decimalPart"
        }
        return parts[0]
    }
    return this
}

// Extension function to convert any object to JSON
inline fun <reified T> T.toJson(): String {
    return Gson().toJson(this)
}

// Extension function to parse JSON back to an object of type T
inline fun <reified T> String.fromJson(): T {
    return Gson().fromJson(this, T::class.java)
}

// Remove padding from composable
fun Modifier.removePadding(sidePadding: Dp): Modifier {
    return this.layout { measurable, constraints ->
        // Measure the composable adding the side padding*2 (left+right)
        val placeable =
            measurable.measure(constraints.offset(horizontal = -sidePadding.roundToPx() * 2))

        //increase the width adding the side padding*2
        layout(
            placeable.width + sidePadding.roundToPx() * 2,
            placeable.height
        ) {
            // Where the composable gets placed
            placeable.place(+sidePadding.roundToPx(), 0)
        }

    }
}


// Check the actual time at a given time
fun LocalDate.checkTheActualTimeAtGivenTime(startTime: String, endTime: String): Boolean {
    val currentTime = LocalTime.now()
    val formatter = DateTimeFormat.forPattern("HH:mm").apply {
        if (Constants.DEFAULT_LANGUAGE == Constants.SAUDI_LANGUAGE_CODE) {
            this.withLocale(Locale(Constants.ARABIC_LANGUAGE_CODE))
        } else {
            this.withLocale(Locale.ENGLISH)
        }
    }

    val parsedStartTime = LocalTime.parse(startTime, formatter)
    val parsedEndTime = LocalTime.parse(endTime, formatter)

    return currentTime.isAfter(parsedStartTime) && currentTime.isBefore(parsedEndTime)
}

// Get current day name
fun LocalDate.getCurrentDayName(): String {
    val formatter = DateTimeFormat.forPattern("EEEE").apply {
        if (Constants.DEFAULT_LANGUAGE == Constants.SAUDI_LANGUAGE_CODE) {
            this.withLocale(Locale(Constants.ARABIC_LANGUAGE_CODE))
        } else {
            this.withLocale(Locale.ENGLISH)
        }
    }
    return formatter.print(this)
}

fun formatDate(inputDate: String): String {
    val inputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd").withLocale(Locale.ENGLISH)
    val outputFormatter = DateTimeFormat.forPattern("dd MMMM (EEEE)").withLocale(Locale.ENGLISH)

    val date = DateTime.parse(inputDate, inputFormatter)
    val formattedDate = date.toString(outputFormatter)

    return formattedDate
}

fun formatTime(inputTime: String): String {
    val inputFormatter = DateTimeFormat.forPattern("HH:mm:ss").withLocale(Locale.ENGLISH)
    val outputFormatter = DateTimeFormat.forPattern("h:mm a").withLocale(Locale.ENGLISH)

    val time = LocalTime.parse(inputTime, inputFormatter)
    val formattedTime = time.toString(outputFormatter)

    return formattedTime
}

// Navigate to google map app with location provided
fun navigateToLocationInGoogleMaps(context: Context, locationQuery: String) {
    val encodedQuery = Uri.encode(locationQuery)
    val uri = Uri.parse("geo:0,0?q=$encodedQuery")
    val intent = Intent(Intent.ACTION_VIEW, uri)
    intent.setPackage("com.google.android.apps.maps")
    context.startActivity(intent)
}

// Convert Timestamp
fun LocalDateTime.convertTimestamp(timestamp: String): String {
    try {
        val inputFormat: DateTimeFormatter =
            DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'").withLocale(Locale.ENGLISH)
        val outputFormat: DateTimeFormatter =
            DateTimeFormat.forPattern("HH:mm a, d MMMM").withLocale(Locale.ENGLISH)

        val dateTime = DateTime.parse(timestamp, inputFormat)
        return dateTime.toString(outputFormat)
    } catch (e: Exception) {
        e.printStackTrace()
        return "Invalid Timestamp"
    }
}

fun LocalDateTime.convertTimestamp123(timestamp: String): String {
    try {
        val inputFormat: DateTimeFormatter =
            DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'").withLocale(Locale.ENGLISH)
        val outputFormat: DateTimeFormatter =
            DateTimeFormat.forPattern("yyyy-MM-dd HH:mm").withLocale(Locale.ENGLISH)

        val dateTime = DateTime.parse(timestamp, inputFormat)
        return dateTime.toString(outputFormat)
    } catch (e: Exception) {
        e.printStackTrace()
        return "Invalid Timestamp"
    }
}

fun LocalDateTime.convertToReadyToPickupDateFormat(inputDate: String): String {
    val inputFormatter: DateTimeFormatter =
        DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'").withLocale(Locale.ENGLISH)
    val outputFormatter: DateTimeFormatter =
        DateTimeFormat.forPattern("d MMMM").withLocale(Locale.ENGLISH)

    try {
        val dateTime = DateTime.parse(inputDate, inputFormatter)
        return outputFormatter.print(dateTime)
    } catch (e: Exception) {
        e.printStackTrace()
        return "Invalid Date"
    }
}

fun Double.roundTo1DecimalPlace(): Double {
    val multiplier = 10.0
    return (this * multiplier).toLong() / multiplier
}

fun LocalDate.formatDateTime(dateTimeStr: String): Pair<String, String> {
    val inputFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
    val outputDateFormat = DateTimeFormat.forPattern("d MMMM (EEEE)")
    val outputTimeFormat = DateTimeFormat.forPattern("h:mm a")

    val dateTime = inputFormat.parseDateTime(dateTimeStr)

    val formattedDate = outputDateFormat.print(dateTime)
    val startTime = outputTimeFormat.print(dateTime)

    val endDateTime = dateTime.plusHours(5) // Add 5 hours to get the end time
    val endTime = outputTimeFormat.print(endDateTime)

    val timeRange = "$startTime to $endTime"

    return Pair(formattedDate, timeRange)
}


@Stable
fun Modifier.mirror(): Modifier {
    return if (Locale.getDefault().layoutDirection == LayoutDirection.RTL)
        this.scale(scaleX = -1f, scaleY = 1f)
    else
        this
}

@Stable
fun enterAnimationItems(): EnterTransition {
    return fadeIn() + expandVertically(expandFrom = Alignment.Top)
}

@Stable
fun exitAnimationItems(): ExitTransition {
    return fadeOut()
}

@Stable
fun enterFadeInAnimation(): EnterTransition {
    return fadeIn()
}

@Stable
fun exitFadeOutAnimation(): ExitTransition {
    return fadeOut()
}
