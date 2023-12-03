package com.bestcoders.zaheed.data.remote

import com.bestcoders.zaheed.data.remote.model.order.CreateOrderRequest
import com.bestcoders.zaheed.data.remote.model.order.CreateOrderResponse
import com.bestcoders.zaheed.data.remote.model.order.GetPurchaseHistoryDetailsResponse
import com.bestcoders.zaheed.data.remote.model.order.GetPurchaseHistoryRequest
import com.bestcoders.zaheed.data.remote.model.order.GetPurchaseHistoryResponse
import com.bestcoders.zaheed.data.remote.model.order.GetTelrUrlRequest
import com.bestcoders.zaheed.data.remote.model.order.GetTelrUrlResponse
import com.bestcoders.zaheed.data.remote.model.order.UpdateOrderRequest
import com.bestcoders.zaheed.data.remote.model.order.UpdateOrderResponse
import com.bestcoders.zaheed.data.remote.model.order.track.OrderTrackResponse
import com.bestcoders.zaheed.data.remote.model.products.AddRemoveProductFavoriteRequest
import com.bestcoders.zaheed.data.remote.model.products.AddRemoveProductFavoriteResponse
import com.bestcoders.zaheed.data.remote.model.products.AddRemoveStoreFavoriteRequest
import com.bestcoders.zaheed.data.remote.model.products.AddRemoveStoreFavoriteResponse
import com.bestcoders.zaheed.data.remote.model.products.FavoriteResponse
import com.bestcoders.zaheed.data.remote.model.products.HomeLayoutRequest
import com.bestcoders.zaheed.data.remote.model.products.HomeLayoutResponse
import com.bestcoders.zaheed.data.remote.model.products.ProductVariationDetailsRequest
import com.bestcoders.zaheed.data.remote.model.products.ProductVariationDetailsResponse
import com.bestcoders.zaheed.data.remote.model.products.SearchResponse
import com.bestcoders.zaheed.data.remote.model.products.SubcategoryResponse
import com.bestcoders.zaheed.data.remote.model.products.cart.AddProductToCartRequest
import com.bestcoders.zaheed.data.remote.model.products.cart.AddProductToCartResponse
import com.bestcoders.zaheed.data.remote.model.products.cart.ChangeProductQuantityRequest
import com.bestcoders.zaheed.data.remote.model.products.cart.ChangeProductQuantityResponse
import com.bestcoders.zaheed.data.remote.model.products.cart.FollowAndUnFollowStoreResponse
import com.bestcoders.zaheed.data.remote.model.products.cart.GetCartByStoreResponse
import com.bestcoders.zaheed.data.remote.model.products.cart.GetCartsResponse
import com.bestcoders.zaheed.data.remote.model.products.cart.RemoveProductFromCartRequest
import com.bestcoders.zaheed.data.remote.model.products.cart.RemoveProductFromCartResponse
import com.bestcoders.zaheed.data.remote.model.products.product_details.ProductDetailsResponse
import com.bestcoders.zaheed.data.remote.model.products.store_details.StoreDetailsResponse
import com.bestcoders.zaheed.data.remote.model.products.store_pickup_locations.StorePickupLocationsResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductsApi {

    // Done
    @POST("layouts/home")
    suspend fun getHomeLayout(
        @Header("Authorization") token: String?,
        @Header("latitude") latitude: String,
        @Header("longitude") longitude: String,
        @Body homeLayoutRequest: HomeLayoutRequest,
        @Query("page") page: Int,
    ): Response<HomeLayoutResponse>

    // Done
    @POST("layouts/main-category-one/{category_id}")
    suspend fun getMainCategory(
        @Header("Authorization") token: String?,
        @Header("latitude") latitude: String,
        @Header("longitude") longitude: String,
        @Path("category_id") categoryId: Int,
        @Query("page") page: Int,
        @Body homeLayoutRequest: HomeLayoutRequest,
    ): Response<HomeLayoutResponse>

    @POST("sub-category/{subcategory_id}")
    suspend fun getSubcategory(
        @Header("Authorization") token: String?,
        @Header("latitude") latitude: String,
        @Header("longitude") longitude: String,
        @Path("subcategory_id") subcategoryId: Int,
        @Query("page") page: Int,
        @Body homeLayoutRequest: HomeLayoutRequest,
    ): Response<SubcategoryResponse>

    // Done
    @GET("wishlists")
    suspend fun getWishlist(
        @Header("Authorization") token: String,
        @Header("latitude") latitude: String,
        @Header("longitude") longitude: String,
        @Query("page") page: Int,
    ): Response<FavoriteResponse>


    // Done
    @POST("wishlists/add")
    suspend fun addProductToWishlist(
        @Header("Authorization") token: String,
        @Body addRemoveProductFavoriteRequest: AddRemoveProductFavoriteRequest,
    ): Response<AddRemoveProductFavoriteResponse>

    // Done
    @POST("wishlists/remove")
    suspend fun removeProductFromWishlist(
        @Header("Authorization") token: String,
        @Body addRemoveProductFavoriteRequest: AddRemoveProductFavoriteRequest,
    ): Response<AddRemoveProductFavoriteResponse>


    @GET("shops/favorite")
    suspend fun getFavoriteShops(
        @Header("Authorization") token: String,
        @Header("latitude") latitude: String,
        @Header("longitude") longitude: String,
        @Query("page") page: Int,
    ): Response<FavoriteResponse>

    // Done
    @POST("shops/favorite/add")
    suspend fun addStoreToFavorite(
        @Header("Authorization") token: String,
        @Body addRemoveStoreFavoriteRequest: AddRemoveStoreFavoriteRequest,
    ): Response<AddRemoveStoreFavoriteResponse>

    // Done
    @POST("shops/favorite/remove")
    suspend fun removeStoreFromFavorite(
        @Header("Authorization") token: String,
        @Body addRemoveStoreFavoriteRequest: AddRemoveStoreFavoriteRequest,
    ): Response<AddRemoveStoreFavoriteResponse>

    @GET("products/search")
    suspend fun getSearchedProducts(
        @Header("Authorization") token: String?,
        @Header("latitude") latitude: String,
        @Header("longitude") longitude: String,
        @Query("name") name: String,
        @Query("page") page: Int,
    ): Response<SearchResponse>

    @GET("shops/search")
    suspend fun getSearchedStores(
        @Header("Authorization") token: String?,
        @Header("latitude") latitude: String,
        @Header("longitude") longitude: String,
        @Query("name") name: String,
        @Query("page") page: Int,
    ): Response<SearchResponse>

    @GET("shops/details/{store_id}")
    suspend fun getStoreDetails(
        @Header("Authorization") token: String?,
        @Header("latitude") latitude: String,
        @Header("longitude") longitude: String,
        @Path("store_id") storeId: Int,
        @Query("page") page: Int,
    ): Response<StoreDetailsResponse>

    @GET("shops/follow/{shop_id}")
    suspend fun followStore(
        @Header("Authorization") token: String?,
        @Path("shop_id") storeId: Int,
    ): Response<FollowAndUnFollowStoreResponse>

    @GET("shops/unfollow/{shop_id}")
    suspend fun unfollowStore(
        @Header("Authorization") token: String?,
        @Path("shop_id") storeId: Int,
    ): Response<FollowAndUnFollowStoreResponse>

    @GET("products/{product_id}")
    suspend fun getProductDetails(
        @Header("Authorization") token: String?,
        @Header("latitude") latitude: String,
        @Header("longitude") longitude: String,
        @Path("product_id") productId: Int,
    ): Response<ProductDetailsResponse>


    @POST("products/{product_id}/variant_details")
    suspend fun getProductVariationDetails(
        @Path("product_id") productId: Int,
        @Body productVariationDetailsRequest: ProductVariationDetailsRequest,
    ): Response<ProductVariationDetailsResponse>

    @POST("carts/add")
    suspend fun addProductToCart(
        @Header("Authorization") token: String?,
        @Header("Temp-User") tempToken: String?,
        @Body addProductToCartRequest: AddProductToCartRequest,
    ): Response<AddProductToCartResponse>

    @POST("carts/remove")
    suspend fun removeProductFromCart(
        @Header("Authorization") token: String?,
        @Header("Temp-User") tempToken: String?,
        @Body removeProductFromCartRequest: RemoveProductFromCartRequest,
    ): Response<RemoveProductFromCartResponse>

    @POST("carts/change-quantity")
    suspend fun changeProductQuantity(
        @Header("Authorization") token: String?,
        @Header("Temp-User") tempToken: String?,
        @Body changeProductQuantityRequest: ChangeProductQuantityRequest,
    ): Response<ChangeProductQuantityResponse>

    @GET("carts")
    suspend fun getCarts(
        @Header("Authorization") token: String?,
        @Header("Temp-User") tempToken: String?,
        @Header("latitude") latitude: String,
        @Header("longitude") longitude: String,
    ): Response<GetCartsResponse>


    @GET("carts/{shop_id}")
    suspend fun getCartByStore(
        @Header("Authorization") token: String?,
        @Header("Temp-User") tempToken: String?,
        @Header("latitude") latitude: String,
        @Header("longitude") longitude: String,
        @Path("shop_id") storeId: Int,
    ): Response<GetCartByStoreResponse>

    @GET("shops/pickup_locations/{shop_id}")
    suspend fun getStorePickupLocations(
        @Path("shop_id") storeId: Int,
    ): Response<StorePickupLocationsResponse>

    @POST("order/store")
    suspend fun createOrder(
        @Header("Authorization") token: String?,
        @Body createOrderRequest: CreateOrderRequest,
    ): Response<CreateOrderResponse>

    @POST("order/update")
    suspend fun updateOrder(
        @Header("Authorization") token: String?,
        @Body updateOrderRequest: UpdateOrderRequest,
    ): Response<UpdateOrderResponse>

    @POST("telr/init")
    suspend fun getTelrUrl(
        @Header("Authorization") token: String?,
        @Body getTelrUrlRequest: GetTelrUrlRequest,
    ): Response<GetTelrUrlResponse>

    @GET("order/track/{order_id}")
    suspend fun getOrderTrack(
        @Header("Authorization") token: String?,
        @Path("order_id") orderId: Int,
    ): Response<OrderTrackResponse>

    @POST("purchase-history")
    suspend fun getPurchaseHistory(
        @Header("Authorization") token: String?,
        @Query("page") page: Int,
        @Body getPurchaseHistoryRequest: GetPurchaseHistoryRequest,
    ): Response<GetPurchaseHistoryResponse>

    @GET("purchase-history/details/{order_id}")
    suspend fun getPurchaseHistoryDetails(
        @Header("Authorization") token: String?,
        @Path("order_id") orderId: Int,
    ): Response<GetPurchaseHistoryDetailsResponse>



}