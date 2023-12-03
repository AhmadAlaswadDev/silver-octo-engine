package com.bestcoders.zaheed.core.util

class DefaultPaginator<Key, Item>(
    private val initialKey: Key,
    private inline val onLoadUpdated: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextKey: Key) -> Resource<Item>,
    private inline val getNextKey: suspend (Item) -> Key,
    private inline val onError: suspend (String?) -> Unit,
    private inline val onSuccess: suspend (items: Item, newKey: Key) -> Unit
) : Paginator<Key, Item> {

    private var currentKey = initialKey
    private var isMakingRequest = false

    override suspend fun loadNextItems() {
        if (isMakingRequest) {
            return
        }
        isMakingRequest = true
        val result = onRequest(currentKey)
        isMakingRequest = false
        when (result) {
            is Resource.Success -> {
                currentKey = getNextKey(result.data!!)
                onSuccess(result.data, currentKey)
                onLoadUpdated(false)
            }

            is Resource.Error -> {
                onError(result.message)
                onLoadUpdated(false)
                return
            }

            is Resource.Loading -> {
                onLoadUpdated(true)
            }
        }

    }

    override fun reset() {
        currentKey = initialKey
    }
}