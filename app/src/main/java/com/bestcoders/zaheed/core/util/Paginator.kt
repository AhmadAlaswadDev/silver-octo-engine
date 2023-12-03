package com.bestcoders.zaheed.core.util

interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    fun reset()
}