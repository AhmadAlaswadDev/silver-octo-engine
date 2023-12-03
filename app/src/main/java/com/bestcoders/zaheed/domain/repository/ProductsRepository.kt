package com.bestcoders.zaheed.domain.repository

import com.bestcoders.zaheed.core.util.Resource
import com.bestcoders.zaheed.domain.model.products.CartByStoreModel
import com.bestcoders.zaheed.domain.model.products.CartModel
import com.bestcoders.zaheed.domain.model.products.Favorite
import com.bestcoders.zaheed.domain.model.products.GetPurchaseHistory
import com.bestcoders.zaheed.domain.model.products.HomeLayout
import com.bestcoders.zaheed.domain.model.products.ProductDetails
import com.bestcoders.zaheed.domain.model.products.ProductVariationDetails
import com.bestcoders.zaheed.domain.model.products.Search
import com.bestcoders.zaheed.domain.model.products.StoreDetails
import com.bestcoders.zaheed.domain.model.products.Subcategory
import com.bestcoders.zaheed.domain.model.track.Order
import com.bestcoders.zaheed.domain.model.track.OrderTrack
import com.bestcoders.zaheed.domain.model.track.PickupPoint

interface ProductsRepository {

    suspend fun getHomeLayout(
        userToken: String?,
        page: Int,
        latitude: String,
        longitude: String,
        priceRangeMax: String,
        priceRangeMin: String,
        sortBy: String,
        distance: String,
        amountOfDiscount: String
    ): Resource<HomeLayout>

    suspend fun getMainCategory(
        userToken: String?,
        page: Int,
        categoryId: Int,
        latitude: String,
        longitude: String,
        priceRangeMax: String,
        priceRangeMin: String,
        sortBy: String,
        distance: String,
        amountOfDiscount: String
    ): Resource<HomeLayout>

    suspend fun getSubcategory(
        userToken: String?,
        page: Int,
        subcategoryId: Int,
        latitude: String,
        longitude: String,
        priceRangeMax: String,
        priceRangeMin: String,
        sortBy: String,
        distance: String,
        amountOfDiscount: String
    ): Resource<Subcategory>

    suspend fun getWishlist(
        page: Int,
        userToken: String,
        latitude: String,
        longitude: String,
    ): Resource<Favorite>

    suspend fun addProductToWishlist(
        userToken: String,
        productId: Int,
    ): Resource<Unit>

    suspend fun removeProductFromWishlist(
        userToken: String,
        productId: Int,
    ): Resource<Unit>

    suspend fun changeProductQuantity(
        productId: String,
        quantity: String,
        variant: String,
    ): Resource<Unit>

    suspend fun getFavoriteShops(
        page: Int,
        userToken: String,
        latitude: String,
        longitude: String,
    ): Resource<Favorite>

    suspend fun addStoreToFavorite(
        userToken: String,
        storeId: Int,
    ): Resource<Unit>

    suspend fun removeStoreFromFavorite(
        userToken: String,
        storeId: Int,
    ): Resource<Unit>

    suspend fun getSearchedProducts(
        userToken: String?,
        page: Int,
        productName: String,
        latitude: String,
        longitude: String
    ): Resource<Search>

    suspend fun getSearchedStores(
        userToken: String?,
        page: Int,
        storeName: String,
        latitude: String,
        longitude: String,
    ): Resource<Search>

    suspend fun getStoreDetails(
        storeId: Int,
        latitude: String,
        longitude: String,
        page: Int
    ): Resource<StoreDetails>

    suspend fun followStore(
        storeId: Int,
    ): Resource<Unit>

    suspend fun unfollowStore(
        storeId: Int,
    ): Resource<Unit>

    suspend fun getProductDetails(
        userToken: String?,
        latitude: String,
        longitude: String,
        productId: Int,
    ): Resource<ProductDetails>


    suspend fun getProductVariationDetails(
        productId: Int,
        variant: String,
    ): Resource<ProductVariationDetails>

    suspend fun addProductToCart(
        productId: Int,
        variant: String,
        quantity: Int,
    ): Resource<String>

    suspend fun removeProductFromCart(
        productId: Int,
        variant: String
    ): Resource<Unit>

    suspend fun getCarts(
        latitude: String,
        longitude: String,
    ): Resource<CartModel>

    suspend fun getCartByStore(
        storeId: Int,
        latitude: String,
        longitude: String,
    ): Resource<CartByStoreModel>

    suspend fun getStorePickupLocations(
        storeId: Int,
    ): Resource<List<PickupPoint>>

    suspend fun createOrder(
        storeId: String,
        paymentType: String,
        preferredTimeToPickUp: String,
        pickupLocationId: String,
    ): Resource<Int>

    suspend fun updateOrder(
        orderId: String,
        paymentType: String,
        preferredTimeToPickUp: String,
        pickupLocationId: String,
    ): Resource<Int>

    suspend fun getTelrUrl(
        orderId: String,
    ): Resource<String>

    suspend fun getOrderTrack(
        orderId: Int
    ): Resource<OrderTrack>

    suspend fun getPurchaseHistory(
        page: Int,
        paymentStatus: String,
        orderStatus: String,
        searchValue: String,
    ): Resource<GetPurchaseHistory>

    suspend fun getPurchaseHistoryDetails(
        orderId: Int,
    ): Resource<Order>

}