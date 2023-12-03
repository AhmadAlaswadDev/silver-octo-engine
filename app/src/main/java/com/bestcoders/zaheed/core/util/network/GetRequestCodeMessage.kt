package com.bestcoders.zaheed.core.util.network

fun getMessageFromRequestCode(requestCode: Int): String {
    return when (requestCode) {
        200 -> "Request successful"
        400 -> "Bad request"
        401 -> "Unauthorized"
        404 -> "Not found"
        500 -> "Internal server error"
        else -> "Unknown request code"
    }
}