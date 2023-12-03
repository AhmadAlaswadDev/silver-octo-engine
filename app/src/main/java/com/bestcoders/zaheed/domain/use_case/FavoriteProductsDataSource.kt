package com.bestcoders.zaheed.domain.use_case

import androidx.compose.runtime.mutableStateListOf
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bestcoders.zaheed.domain.model.products.Store
import com.bestcoders.zaheed.domain.repository.ProductsRepository

class FavoriteProductsDataSource(
    private val repository: ProductsRepository,
    private val userToken: String,
    private val userLatitude: String,
    private val userLongitude: String,
) : PagingSource<Int, Store>() {

    override fun getRefreshKey(state: PagingState<Int, Store>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Store> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = repository.getFavoriteShops(
                page = nextPageNumber,
                userToken = userToken,
                latitude = userLatitude,
                longitude = userLongitude,
            )
            LoadResult.Page(
                data = response.data?.storeWithProducts ?: mutableStateListOf(),
                prevKey = null,
                nextKey = if (response.data?.storeWithProducts != null && response.data.storeWithProducts.isNotEmpty())  nextPageNumber + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
