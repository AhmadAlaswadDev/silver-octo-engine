package com.bestcoders.zaheed.data.remote.model.products

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PaginationMetaResponse(
    val current_page: Int,
    val total_pages: Int,
    val next_page: Int?,
    val next_page_url: String?,
    val total_items: String?,
) : Parcelable {
    fun toPaginationMeta(): PaginationMeta {
        return PaginationMeta(
            currentPage = this.current_page,
            nextPage = this.next_page,
            totalPages = this.total_pages,
            nextPageUrl = this.next_page_url,
            totalItems = this.total_items
        )
    }
}

data class PaginationMeta(
    val currentPage: Int,
    val totalPages: Int,
    val nextPage: Int?,
    val nextPageUrl: String?,
    val totalItems: String?,
)
